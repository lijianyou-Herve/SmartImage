package com.herve.library.wedgetlib;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

public class GuideView extends AppCompatImageView {
    public GuideView(Context context) {
        super(context);
    }

    public GuideView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GuideView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
