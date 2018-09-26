package com.herve.library.wedgetlib;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;

public class MulStructureView extends AppCompatImageView {

    private Paint buttonPaint;//中间按钮的颜色
    private Paint paint;//边框虚线的颜色
    private PathEffect effect;//虚线的信息
    private RectF roundRect;//虚线的矩形
    private Rect drawRect;//图片的位置
    private float roundR = 2;//圆角

    private float solidLineLength = 10;//实现的长度
    private float dottedLineLength = 2;//虚线的长度
    private float crossLineLength = 16;//十字的长度
    private float circleRadius = 40;//圆的半径

    private float mViewWidth;
    private float mViewHeight;

    private boolean drawCross = true;

    private String TAG = getClass().getSimpleName();

    public MulStructureView(Context context) {
        this(context, null);
    }

    public MulStructureView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MulStructureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {

        paint = new Paint();
        paint.setColor(Color.YELLOW);
        paint.setStyle(Paint.Style.STROKE);//设置画笔style空心
        paint.setAntiAlias(true);
        paint.setStrokeWidth(roundR);

        effect = new DashPathEffect(new float[]{solidLineLength, dottedLineLength, solidLineLength, dottedLineLength}, roundR);//虚线

        buttonPaint = new Paint();
        buttonPaint.setColor(Color.WHITE);
        buttonPaint.setStyle(Paint.Style.FILL);//设置画笔style实心
        buttonPaint.setAntiAlias(true);
        buttonPaint.setStrokeWidth(roundR * 2);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, w, oldw, oldw);
        if (w > 0 && w > 0) {
            mViewWidth = w;
            mViewHeight = w;
            setRect();
        }
    }

    public void setLineColor(@ColorInt int color) {
        paint.setColor(color);
        invalidate();
    }

    private void setRect() {
        roundRect = new RectF();
        roundRect.left = roundR * 2;
        roundRect.right = mViewWidth - roundR * 2;
        roundRect.top = roundR * 2;
        roundRect.bottom = mViewHeight - roundR * 2;

        float minSize = Math.min(mViewWidth, mViewHeight);
        if (circleRadius > (minSize / 2)) {
            circleRadius = minSize / 2;
            crossLineLength = circleRadius * 2 / 5;
        }
    }

    /**
     * Draw round rect.
     *
     * @param canvas  the canvas 画笔
     * @param centerX the center 矩形中心点X
     * @param centerY the center 矩形中心点Y
     * @param width   the width 矩形宽度
     * @param height  the height 矩形豪赌
     */
    private void drawRoundRect(Canvas canvas, float centerX, float centerY, float width, float height, int position) {
        drawDrawable(canvas, centerX, centerY, width, height, position);
        canvas.save();
        //虚线
        drawLine(canvas, centerX, centerY, width, height);

        if (drawCross) {
            drawCross(canvas, centerX, centerY, width, height, position);
        }

        canvas.restore();
    }

    private void drawCross(Canvas canvas, float centerX, float centerY, float width, float height, int position) {
        if (drawables == null || drawables.size() == 0 || drawables.get(position) == null) {
            //圆圈
            buttonPaint.setColor(Color.WHITE);
            float minSize = Math.min(width, height);
            if (circleRadius > (minSize / 2)) {
                circleRadius = minSize / 2;
                crossLineLength = circleRadius * 2 / 5;
            }
            canvas.drawCircle(centerX, centerY, circleRadius, buttonPaint);
            //十字
            buttonPaint.setColor(Color.BLACK);
            canvas.drawLine(centerX, centerY - crossLineLength, centerX, centerY + crossLineLength, buttonPaint);
            canvas.drawLine(centerX - crossLineLength, centerY, centerX + crossLineLength, centerY, buttonPaint);
        }
    }

    private void drawLine(Canvas canvas, float centerX, float centerY, float width, float height) {
        roundRect.left = centerX - width / 2 + roundR * 2;
        roundRect.right = centerX + width / 2 - roundR * 2;
        roundRect.top = centerY - height / 2 + roundR * 2;
        roundRect.bottom = centerY + height / 2 - roundR * 2;

        paint.setPathEffect(effect);
        canvas.drawRoundRect(roundRect, roundR, roundR, paint);
    }

    private void drawDrawable(Canvas canvas, float centerX, float centerY, float width, float height, int position) {

        if (drawables == null || drawables.size() == 0 || drawables.get(position) == null) {
            return;
        }

        Drawable drawable = drawables.get(position);

        float drawWidth = drawable.getIntrinsicWidth();
        float drawHeight = drawable.getIntrinsicHeight();
//        Matrix matrix = new Matrix();
//        canvas.concat(matrix);
        drawRect = new Rect();
        drawRect.left = (int) (centerX - width / 2 + roundR * 2);
        drawRect.right = (int) (centerX + width / 2 - roundR * 2);
        drawRect.top = (int) (centerY - width * (drawHeight / drawWidth) / 2 + roundR * 2);
        drawRect.bottom = (int) (centerY + width * (drawHeight / drawWidth) / 2 - roundR * 2);
        if (drawRect.top < centerY - height / 2 + roundR * 2) {
            drawRect.top = (int) (centerY - height / 2 + roundR * 2);
        }
        if (drawRect.bottom > centerY + height / 2 - roundR * 2) {
            drawRect.bottom = (int) (centerY + height / 2 - roundR * 2);
        }

        drawable.setBounds(drawRect);

        drawable.draw(canvas);
    }

    public enum Model {
        Single,//单个
        Zyg,//左右对称
        Sym,//上下对称
        FourSplit,//四分屏
    }

    private Model mModel = Model.FourSplit;

    private float downX = -1;
    private float downY = -1;

    public void setData(Model model, SparseArray<Drawable> drawables) {
        this.mModel = model;
        this.drawables = drawables;
        invalidate();
    }

    public void setDrawables(SparseArray<Drawable> drawables) {
        this.drawables = drawables;
        invalidate();
    }

    public SparseArray<Drawable> getDrawables(){
        return drawables;
    }

    public void setModel(Model model) {
        this.mModel = model;
        invalidate();
    }

    public void hideAddIcon() {
        this.drawCross = false;
    }

    public void showAddIcon() {
        this.drawCross = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!drawCross) {
            return super.onTouchEvent(event);
        }
        int action = MotionEventCompat.getActionMasked(event);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "onTouchEvent: ACTION_DOWN");

                downX = event.getX();
                downY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float upDownX = event.getX();
                float upDownY = event.getY();
                Log.i(TAG, "onTouchEvent: upDownX=" + upDownX);
                Log.i(TAG, "onTouchEvent: upDownY" + upDownY);
                if (upDownX < 0 || upDownX > getWidth() || upDownY < 0 || upDownY > getHeight()) {
                    downX = -1;
                    downX = -1;
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "onTouchEvent: ACTION_UP");
                if (downX >= 0 && downY >= 0) {
                    switch (mModel) {
                        case Single:
                            if (downX <= mViewWidth && downY <= mViewHeight) {
                                onButtonPressed(0, downX, downY);
                            }
                            break;
                        case Zyg:
                            if (downX <= mViewWidth / 2 && downY <= mViewHeight) {
                                onButtonPressed(0, downX, downY);
                            } else {
                                onButtonPressed(1, downX, downY);
                            }
                            break;
                        case Sym:
                            if (downX <= mViewWidth && downY <= mViewHeight / 2) {
                                onButtonPressed(0, downX, downY);
                            } else {
                                onButtonPressed(1, downX, downY);
                            }
                            break;
                        case FourSplit:
                            if (downX <= mViewWidth / 2 && downY <= mViewHeight / 2) {
                                onButtonPressed(0, downX, downY);
                            } else if (downX >= mViewWidth / 2 && downY <= mViewHeight / 2) {
                                onButtonPressed(1, downX, downY);
                            } else if (downX <= mViewWidth / 2 && downY >= mViewHeight / 2) {
                                onButtonPressed(2, downX, downY);
                            } else if (downX >= mViewWidth / 2 && downY >= mViewHeight / 2) {
                                onButtonPressed(3, downX, downY);
                            }
                            break;
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.i(TAG, "onTouchEvent: ACTION_CANCEL");
                downX = -1;
                downX = -1;
                break;
        }
        return true;
    }

    private void onButtonPressed(int position, float downX, float downY) {
        if (mOnItemStructureClickListener != null) {
            playSoundEffect(SoundEffectConstants.CLICK);
            mOnItemStructureClickListener.onItemClick(mModel, position, downX, downY);
        }
    }

    private OnItemStructureClickListener mOnItemStructureClickListener;

    public void setOnItemStructureClickListener(OnItemStructureClickListener onItemStructureClickListener) {
        this.mOnItemStructureClickListener = onItemStructureClickListener;
    }

    public interface OnItemStructureClickListener {
        void onItemClick(Model model, int position, float downX, float downY);
    }

    private SparseArray<Drawable> drawables;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (mModel) {
            case Single:
                drawRoundRect(canvas, mViewWidth / 2, mViewHeight / 2, mViewWidth, mViewHeight, 0);
                break;
            case Zyg:
                drawRoundRect(canvas, mViewWidth / 4, mViewHeight / 2, mViewWidth / 2, mViewHeight, 0);
                drawRoundRect(canvas, mViewWidth * 3 / 4, mViewHeight / 2, mViewWidth / 2, mViewHeight, 1);
                break;
            case Sym:
                drawRoundRect(canvas, mViewWidth / 2, mViewHeight / 4, mViewWidth, mViewHeight / 2, 0);
                drawRoundRect(canvas, mViewWidth / 2, mViewHeight * 3 / 4, mViewWidth, mViewHeight / 2, 1);
                break;
            case FourSplit:

                drawRoundRect(canvas, mViewWidth / 4, mViewHeight / 4, mViewWidth / 2, mViewHeight / 2, 0);
                drawRoundRect(canvas, mViewWidth * 3 / 4, mViewHeight / 4, mViewWidth / 2, mViewHeight / 2, 1);

                drawRoundRect(canvas, mViewWidth / 4, mViewHeight * 3 / 4, mViewWidth / 2, mViewHeight / 2, 2);
                drawRoundRect(canvas, mViewWidth * 3 / 4, mViewHeight * 3 / 4, mViewWidth / 2, mViewHeight / 2, 3);
                break;
        }
    }
}
