package com.soli.canvas

import android.content.Context
import android.graphics.*
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.os.Build
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
    private val bitmap: Bitmap
    private val cycle: Bitmap

    init {
        BaseHelper.loadWinSize(context, mWinSize)
        bitmap = BitmapFactory.decodeResource(context.resources, R.mipmap.moren_user_pager)
        cycle = makeCircle(200)
    }

    private fun resetToDefault(canvas: Canvas) {
        BaseHelper.drawGrid(canvas, mWinSize, mGridPaint)
        BaseHelper.drawCoo(canvas, Point(0, 0), mWinSize, mGridPaint)
        BaseHelper.drawCoo(canvas, mCoo, mWinSize, mGridPaint)
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        drawColor(canvas!!)
        resetToDefault(canvas)

        val layerId =
            canvas.saveLayer(0f, 0f, mWinSize.x.toFloat(), mWinSize.y.toFloat(), mGridPaint)
        mGridPaint.color = Color.RED
        mGridPaint.strokeWidth = 20f
        canvas.drawPoint(200f, 100f, mGridPaint)

        mGridPaint.strokeWidth = 5f
        var rectArc = RectF((100 + 500).toFloat(), 100f, (500 + 500).toFloat(), 300f)
        canvas.drawRect(rectArc, mGridPaint)
        canvas.drawArc(rectArc, 0f, 180f, true, mGridPaint)
        rectArc = RectF(900f, 400f, 1300f, 700f)
        canvas.drawRect(rectArc, mGridPaint)
        mGridPaint.style = Paint.Style.FILL
        canvas.drawArc(rectArc, 0f, 180f, false, mGridPaint)

        canvas.drawRect(
            mCoo.x + 100f,
            mCoo.y + 100f,
            mCoo.x + 100f * 2,
            mCoo.y + 100f * 2,
            mGridPaint
        )

        canvas.save()
        canvas.translate(mCoo.x.toFloat(), mCoo.y.toFloat())
        canvas.rotate(30f)
        canvas.scale(2f, 2f)
        canvas.drawRect(100f, 100f, 100f * 2, 100f * 2, mGridPaint)
        canvas.restore()

        val rect = Rect(100, 150, 250, 350)
        mGridPaint.color = Color.GREEN
        mGridPaint.style = Paint.Style.STROKE
        canvas.drawRect(rect, mGridPaint)
        canvas.save()
        canvas.clipRect(rect)
        mGridPaint.color = Color.YELLOW
        mGridPaint.style = Paint.Style.FILL
        canvas.drawRect(0f, 0f, 300f, 300f, mGridPaint)
        canvas.restore()
        mGridPaint.color = Color.BLACK


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            canvas.save()
            canvas.clipOutRect(rect)
            canvas.drawRect(100f, 100f, 300f, 300f, mGridPaint)
            canvas.restore()
        }


        canvas.save()
        canvas.translate(mWinSize.x / 2f, mWinSize.y / 2f)
        mGridPaint.color = Color.BLUE
        mGridPaint.style = Paint.Style.FILL
//        mGridPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
        canvas.drawRect(-50f, -50f, 250f, 250f, mGridPaint)
        mGridPaint.color = Color.YELLOW
        mGridPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_OUT)
        mGridPaint.shader = LinearGradient(
            -100f,
            -100f,
            -100f,
            100f,
            intArrayOf(Color.RED, Color.GREEN, 0), null,
            Shader.TileMode.CLAMP
        )
        canvas.drawCircle(0f, 0f, 150f, mGridPaint)
        mGridPaint.xfermode = null
        mGridPaint.shader = null
        canvas.restore()

        canvas.save()
        canvas.translate(mCoo.x - 200f, mCoo.y + 200f)
//        canvas.clipRect(-200f,-200f,200f,200f)
        canvas.drawBitmap(cycle, -100f, 100f, mGridPaint)
        mGridPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP)
        canvas.drawBitmap(bitmap, Matrix().apply {
            postScale(0.3f, 0.3f)
            postTranslate(-100f,100f)
        }, mGridPaint)
        mGridPaint.xfermode = null
        canvas.restore()

        canvas.restoreToCount(layerId)
    }

    /**
     * 生成圆形Bitmap
     * @return
     */
    private fun makeCircle(size: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint = Paint(ANTI_ALIAS_FLAG)
        paint.color = Color.BLUE
        paint.style = Paint.Style.FILL
        val radius: Int = size / 2
        canvas.drawCircle(size / 2f, size / 2f, radius.toFloat(), paint)
        return bitmap
    }

    /**
     * 绘制颜色(注意在画坐标系前绘制，否则后者覆盖)
     * @param canvas
     */
    private fun drawColor(canvas: Canvas) {
        canvas.drawColor(Color.parseColor("#E0F7F5"))
    }

}