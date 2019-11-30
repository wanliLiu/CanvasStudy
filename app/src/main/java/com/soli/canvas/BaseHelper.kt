package com.soli.canvas

import android.content.Context
import android.graphics.*
import android.util.DisplayMetrics
import android.view.WindowManager


/*
 * @author soli
 * @Time 2019-11-30 11:19
 */
object BaseHelper {


    /**
     * 获得屏幕高度
     *
     * @param ctx 上下文
     * @param winSize 屏幕尺寸
     */
    fun loadWinSize(ctx: Context, winSize: Point) {
        val wm = ctx.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        wm.defaultDisplay?.getMetrics(outMetrics)
        winSize.x = outMetrics.widthPixels
        winSize.y = outMetrics.heightPixels
    }

    /**
     * 绘制网格:注意只有用path才能绘制虚线
     *
     * @param step 小正方形边长
     * @param winSize 屏幕尺寸
     */
    private fun gridPath(step: Int, winSize: Point): Path {
        val path = Path()
        for (i in 0 until winSize.y / step + 1) {
            path.moveTo(0f, (step * i).toFloat())
            path.lineTo(winSize.x.toFloat(), (step * i).toFloat())
        }
        for (i in 0 until winSize.x / step + 1) {
            path.moveTo((step * i).toFloat(), 0f)
            path.lineTo((step * i).toFloat(), winSize.y.toFloat())
        }
        return path
    }

    /**
     * 绘制网格
     * @param canvas 画布
     * @param winSize 屏幕尺寸
     * @param paint 画笔
     */
    fun drawGrid(
        canvas: Canvas,
        winSize: Point,
        paint: Paint
    ) { //初始化网格画笔
        paint.strokeWidth = 2f
        paint.color = Color.GRAY
        paint.style = Paint.Style.STROKE
        //设置虚线效果new float[]{可见长度, 不可见长度},偏移值
        paint.pathEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
        canvas.drawPath(gridPath(50, winSize), paint)
    }

    /**
     * 坐标系路径
     *
     * @param coo     坐标点
     * @param winSize 屏幕尺寸
     * @return 坐标系路径
     */
    private fun cooPath(coo: Point, winSize: Point): Path {
        val path = Path()
        //x正半轴线
        path.moveTo(coo.x.toFloat(), coo.y.toFloat())
        path.lineTo(winSize.x.toFloat(), coo.y.toFloat())
        //x负半轴线
        path.moveTo(coo.x.toFloat(), coo.y.toFloat())
        path.lineTo((coo.x - winSize.x).toFloat(), coo.y.toFloat())
        //y负半轴线
        path.moveTo(coo.x.toFloat(), coo.y.toFloat())
        path.lineTo(coo.x.toFloat(), (coo.y - winSize.y).toFloat())
        //y负半轴线
        path.moveTo(coo.x.toFloat(), coo.y.toFloat())
        path.lineTo(coo.x.toFloat(), winSize.y.toFloat())
        return path
    }

    /**
     * 绘制坐标系
     * @param canvas  画布
     * @param coo     坐标系原点
     * @param winSize 屏幕尺寸
     * @param paint   画笔
     */
    fun drawCoo(
        canvas: Canvas,
        coo: Point,
        winSize: Point,
        paint: Paint
    ) { //初始化网格画笔
        paint.strokeWidth = 4f
        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE
        //设置虚线效果new float[]{可见长度, 不可见长度},偏移值
        paint.pathEffect = null
        //绘制直线
        canvas.drawPath(cooPath(coo, winSize), paint)
        //左箭头
        canvas.drawLine(
            winSize.x.toFloat(),
            coo.y.toFloat(),
            (winSize.x - 40).toFloat(),
            (coo.y - 20).toFloat(), paint
        )
        canvas.drawLine(
            winSize.x.toFloat(),
            coo.y.toFloat(),
            (winSize.x - 40).toFloat(),
            (coo.y + 20).toFloat(), paint
        )
        //下箭头
        canvas.drawLine(
            coo.x.toFloat(),
            winSize.y.toFloat(),
            (coo.x - 20).toFloat(),
            (winSize.y - 40).toFloat(), paint
        )
        canvas.drawLine(
            coo.x.toFloat(),
            winSize.y.toFloat(),
            (coo.x + 20).toFloat(),
            (winSize.y - 40).toFloat(), paint
        )
        //为坐标系绘制文字
        drawText4Coo(canvas, coo, winSize, paint)
    }

    /**
     * 为坐标系绘制文字
     *
     * @param canvas  画布
     * @param coo     坐标系原点
     * @param winSize 屏幕尺寸
     * @param paint   画笔
     */
    private fun drawText4Coo(
        canvas: Canvas,
        coo: Point,
        winSize: Point,
        paint: Paint
    ) { //绘制文字
        paint.textSize = 50f
        canvas.drawText("x", (winSize.x - 60).toFloat(), (coo.y - 40).toFloat(), paint)
        canvas.drawText("y", (coo.x - 40).toFloat(), (winSize.y - 60).toFloat(), paint)
        paint.textSize = 25f
        //X正轴文字
        for (i in 1 until (winSize.x - coo.x) / 50) {
            paint.strokeWidth = 2f
            canvas.drawText(
                (100 * i).toString(),
                (coo.x - 20 + 100 * i).toFloat(),
                (coo.y + 40).toFloat(), paint
            )
            paint.strokeWidth = 5f
            canvas.drawLine(
                (coo.x + 100 * i).toFloat(),
                coo.y.toFloat(),
                (coo.x + 100 * i).toFloat(),
                (coo.y - 10).toFloat(), paint
            )
        }
        //X负轴文字
        for (i in 1 until coo.x / 50) {
            paint.strokeWidth = 2f
            canvas.drawText(
                (-100 * i).toString(),
                (coo.x - 20 - 100 * i).toFloat(),
                (coo.y + 40).toFloat(), paint
            )
            paint.strokeWidth = 5f
            canvas.drawLine(
                (coo.x - 100 * i).toFloat(),
                coo.y.toFloat(),
                (coo.x - 100 * i).toFloat(),
                (coo.y - 10).toFloat(), paint
            )
        }
        //y正轴文字
        for (i in 1 until (winSize.y - coo.y) / 50) {
            paint.strokeWidth = 2f
            canvas.drawText(
                (100 * i).toString(),
                (coo.x + 20).toFloat(),
                (coo.y + 10 + 100 * i).toFloat(), paint
            )
            paint.strokeWidth = 5f
            canvas.drawLine(
                coo.x.toFloat(),
                (coo.y + 100 * i).toFloat(),
                (coo.x + 10).toFloat(),
                (coo.y + 100 * i).toFloat(), paint
            )
        }
        //y负轴文字
        for (i in 1 until coo.y / 50) {
            paint.strokeWidth = 2f
            canvas.drawText(
                (-100 * i).toString(),
                (coo.x + 20).toFloat(),
                (coo.y + 10 - 100 * i).toFloat(), paint
            )
            paint.strokeWidth = 5f
            canvas.drawLine(
                coo.x.toFloat(),
                (coo.y - 100 * i).toFloat(),
                (coo.x + 10).toFloat(),
                (coo.y - 100 * i).toFloat(), paint
            )
        }
    }
}