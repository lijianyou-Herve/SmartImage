package com.herve.library.wedgetlib;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;

import org.jetbrains.annotations.Nullable;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * Created by Lijianyou on 2018-09-27.
 *
 * @author Lijianyou
 */
public class GuideView extends AppCompatImageView {

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
    private boolean mShowGuide = true;

    public GuideView(Context context) {
        this(context, null);
    }

    public GuideView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GuideView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldw);
        if (w > 0 && h > 0) {
            mViewWidth = w;
            mViewHeight = h;
            setRect();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    private void initialize() {

        paint = new Paint();
        paint.setColor(Color.RED);
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

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isShowGuide()) {
            drawRoundRect(canvas, mViewWidth / 2, mViewHeight / 2, mViewWidth, mViewHeight, 0);
        }
    }

    public void showGuide(boolean showGuide) {
        this.mShowGuide = showGuide;
    }

    public boolean isShowGuide() {
        return mShowGuide;
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
        canvas.save();
        //虚线
        drawLine(canvas, centerX, centerY, width, height);

        if (drawCross) {
//            drawCross(canvas, centerX, centerY, width, height, position);
        }

        canvas.restore();
    }

    private void drawCross(Canvas canvas, float centerX, float centerY, float width, float height, int position) {
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

    private void drawLine(Canvas canvas, float centerX, float centerY, float width, float height) {
        roundRect.left = centerX - width / 2 + roundR * 2;
        roundRect.right = centerX + width / 2 - roundR * 2;
        roundRect.top = centerY - height / 2 + roundR * 2;
        roundRect.bottom = centerY + height / 2 - roundR * 2;

        paint.setPathEffect(effect);
        canvas.drawRoundRect(roundRect, roundR, roundR, paint);
    }
}
