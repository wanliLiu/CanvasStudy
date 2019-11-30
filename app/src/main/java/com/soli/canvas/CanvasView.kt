package com.soli.canvas

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import android.util.AttributeSet
import android.view.View

/*
 * @author soli
 * @Time 2019-11-30 11:15
 */
class CanvasView : View {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )


    private val mGridPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mWinSize = Point()
    private val mCoo = Point(500, 500)

    init {
        BaseHelper.loadWinSize(context, mWinSize)
    }

    private fun resetToDefault(canvas: Canvas) {
        BaseHelper.drawGrid(canvas, mWinSize, mGridPaint)
        BaseHelper.drawCoo(canvas, Point(0,0), mWinSize, mGridPaint)
        BaseHelper.drawCoo(canvas, mCoo, mWinSize, mGridPaint)
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        resetToDefault(canvas!!)

    }
}