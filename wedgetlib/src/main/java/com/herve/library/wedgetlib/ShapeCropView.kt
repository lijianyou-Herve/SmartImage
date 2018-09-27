package com.herve.library.wedgetlib

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import androidx.annotation.IntDef

/**
 * Created by Lijianyou on 2018-09-26.
 * @author  Lijianyou
 *
 */


class ShapeCropView : View {
    @Retention(AnnotationRetention.SOURCE)
    @IntDef(CIRCLE, RANGE, TRIANGLE)
    annotation class Shape

    companion object {
        const val CIRCLE: Int = 0x00
        const val RANGE: Int = 0x01
        const val TRIANGLE: Int = 0x02
    }

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        getTheme(attrs)
    }

    private fun getTheme(attrs: AttributeSet?) {
        val typedArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.ShapeCropView)
        typedArray.recycle();
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
    }
}