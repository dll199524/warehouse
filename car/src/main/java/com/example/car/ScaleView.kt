package com.example.car

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.OverScroller
import kotlin.math.absoluteValue

class ScaleView constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int
) : View(context, attrs) {

    private var longScaleHeight = 50.dp
    private var shortScaleHeight = 40.dp
    private var scaleWidth = 1.5f.dp
    private var scaleSpace = 11.dp
    private var scaleColor = Color.BLACK
    private var scaleMaxVal = 155
    private var scaleMinVal = 0
    private var scaleCount = 0
    private val pointHeight = 70.dp
    private val pointWidth = 4.dp
    private var viewWidth = 0
    private var viewHeight = 0

    private var currentValue = 0
    private val linePaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = scaleColor
            strokeWidth = scaleWidth.toFloat()
        }
    }
    private val pointPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.RED
            strokeWidth = pointWidth.toFloat()
        }
    }

    init {
        scaleCount = scaleMaxVal - scaleMinVal
        currentValue = scaleCount / 2
    }

    /*************************************测量******************************************************/
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(
            MeasureSpec.getSize(widthMeasureSpec),
            measureHeight(heightMeasureSpec)
        )
    }

    private fun measureHeight(heightMeasureSpec: Int): Int {
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        var height = 0
        when (heightMode) {
            MeasureSpec.EXACTLY -> {
                height = heightSize
            }
            MeasureSpec.AT_MOST -> {
                height = longScaleHeight.coerceAtLeast(pointHeight) + paddingTop + paddingBottom
                height = height.coerceAtLeast(heightSize)
            }
            MeasureSpec.UNSPECIFIED -> {
                height = heightSize
            }
        }
        return height
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        viewWidth = w
        viewHeight = h
        initParams()
    }

    private fun initParams() {
        longScaleHeight = height - paddingTop - paddingBottom
        shortScaleHeight = longScaleHeight - 10.dp
    }

    /*************************************布局******************************************************/
    fun setCurrentValue(value: Int) {
        var value = value
        if (value > scaleMaxVal) value = scaleMaxVal
        if (value < scaleMinVal) value = scaleMinVal
        currentValue = value
        requestLayout()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        val x: Int = (currentValue - scaleMinVal) * scaleSpace - viewWidth / 2
        scrollTo(x, 0)
    }

    /*************************************绘制******************************************************/
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        for (i in 0..scaleCount) {
            val x1 = scaleSpace * i
            val y1 = height - paddingBottom
            val x2 = x1
            val y2 = height - paddingBottom - (if (i % 10 == 0) longScaleHeight else shortScaleHeight)
            canvas?.drawLine(
                x1.toFloat(),
                y1.toFloat(),
                x2.toFloat(),
                y2.toFloat(),
                linePaint)
        }
        drawLinePoint(canvas)
    }

    private fun drawLinePoint(canvas: Canvas?) {
        val centerPointX = viewWidth / 2 + scrollX
        val centerPointStartY = paddingTop - 5.dp
        val centerPointEndY = viewHeight - paddingBottom + 5.dp
        canvas?.drawLine(
            centerPointX.toFloat(),
            centerPointStartY.toFloat(),
            centerPointX.toFloat(),
            centerPointEndY.toFloat(),
            pointPaint
        )
    }

    /*************************************触摸******************************************************/
    private val scroller by lazy { OverScroller(context) }
    private val gestureDetector by lazy { GestureDetector(context, touchGestureListener) }
    private val touchGestureListener = object: GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }
        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent?,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            scrollBy(distanceX.toInt(), 0)
            return true
        }

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent?,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            scroller.fling(
                scrollX, 0,
                -velocityX.toInt() / 2, 0,
                -viewWidth / 2,  (scaleCount - 1) * scaleSpace - viewWidth / 2, 0, 0,
                viewWidth / 4, 0
            )
            return true
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_UP || event?.action == MotionEvent.ACTION_CANCEL)
            correctPosition()
        return gestureDetector.onTouchEvent(event)
    }

    private var currentX = 0

    override fun scrollTo(x: Int, y: Int) {
        Log.d("TAG", "scrollTo: ")
        var x = x
        if (x < -viewWidth / 2) x = -viewWidth / 2
        if (x > (scaleCount) * scaleSpace - viewWidth / 2)
            x = (scaleCount) * scaleSpace - viewWidth / 2
        super.scrollTo(x, y)
        currentValue = (x + viewWidth / 2) / scaleSpace + scaleMinVal
        currentX = x
        invalidate()
    }

    override fun computeScroll() {
        // 如果滚动器正在滚动，更新滚动的位置，并根据需要修正位置
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.currX, scroller.currY)
            correctPosition()
        }
        invalidate()
    }

    // 修正位置,计算当前选中值距离最近的刻度的偏移量，并根据偏移量进行平滑滚动到正确的位置
    private fun correctPosition() {
        // 刻度值对应的x坐标
        val scaleX: Int = (currentValue - scaleMinVal) * scaleSpace - viewWidth / 2
        // 偏移值 = 刻度值对应的x坐标-当前x坐标 的绝对值
        val offset = (scaleX - currentX).absoluteValue
        if (offset == 0) {
            return
        }
        if (offset > scaleSpace / 2) {
            smoothScrollBy(scaleSpace - offset)
        } else {
            smoothScrollBy(-offset)
        }
    }

    // 平滑滚动方法
    private fun smoothScrollBy(dx: Int) {
        // 启动滚动器，设置滚动的起始位置，距离，时间和插值器
        scroller.startScroll(scrollX, 0, dx, 0, 200)
        invalidate()
    }

}