package com.example.car

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView

class CustomSurfaceView constructor(context: Context,
                                    attrs: AttributeSet? = null,) :
                                    SurfaceView(context, attrs), SurfaceHolder.Callback {

    private val paint : Paint
    private val path : Path
    private val surfaceHolder : SurfaceHolder
    private var flag : Boolean = false
    init {
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.BLUE
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 5.0f
        path = Path()
        surfaceHolder = getHolder()
        surfaceHolder?.addCallback(this)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_MOVE-> {
                path.moveTo(event.x, event.y)
                return true
            }
            MotionEvent.ACTION_DOWN-> {
                path.lineTo(event.x, event.y)
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        Thread {
            while (flag) {
                val canvas = surfaceHolder.lockCanvas()
                canvas.drawPath(path, paint)
                surfaceHolder.unlockCanvasAndPost(canvas)
            }
        }.start()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        flag = false
    }
}