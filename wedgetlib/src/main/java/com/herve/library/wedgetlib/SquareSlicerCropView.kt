package com.herve.library.wedgetlib

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * Created by Lijianyou on 2018-09-26.
 * @author  Lijianyou
 *
 */


class SquareSlicerCropView : View {

    private var mItemCount: Int = 1
    private var mSpanCount: Int = 1
    private lateinit var paint: Paint


    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        getTheme(attrs)
    }

    private fun getTheme(attrs: AttributeSet?) {
        val typedArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.SquareSlicerCropView)
        mItemCount = typedArray.getInt(R.styleable.SquareSlicerCropView_itemCount, 0)
        mSpanCount = typedArray.getInt(R.styleable.SquareSlicerCropView_itemCount, 0)

        typedArray.recycle()

        init()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    private fun init() {

        paint = Paint()
        paint.color = Color.WHITE
        paint.strokeWidth = 2f
        paint.isAntiAlias = true
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
    }

    fun drawLine(canvas: Canvas) {

    }
}