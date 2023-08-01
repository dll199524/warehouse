package com.example.customview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.customview.R;

public class MultiTouchView extends View {

    private Paint mPaint;
    private Bitmap mBitmap;
    private float mDownX, mDownY;
    private float mOffsetX, mOffsetY;
    private float mLastOffsetX, mLastOffsetY;
    private int mCurrentIndex;

    public MultiTouchView(Context context) {
        this(context, null);
    }

    public MultiTouchView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultiTouchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.photo);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, mOffsetX, mOffsetY, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 多指操作的时候调用方法：event.getActionMasked
        switch (event.getActionMasked()) {
            // 第一个手指按下时触发，只会触发一次
            case MotionEvent.ACTION_DOWN:
                mDownX = event.getX();
                mDownY = event.getY();
                mCurrentIndex = 0;
                break;
            // 所有手指的移动都是触发这个事件
            case MotionEvent.ACTION_MOVE:
                int index = event.findPointerIndex(mCurrentIndex);
                mOffsetX = event.getX(index) + mLastOffsetX - mDownX;
                mOffsetY = event.getY(index) + mLastOffsetY - mDownY;
                invalidate();
                break;
            // 只会触发一次，最后一根手指抬起时触发
            case MotionEvent.ACTION_UP:
                mLastOffsetX = mOffsetX;
                mLastOffsetY = mOffsetY;
                break;
            // 非第一根手指按下，触发
            case MotionEvent.ACTION_POINTER_DOWN:
                int actionIndex = event.getActionIndex();
                mCurrentIndex = event.getPointerId(actionIndex);
                mDownX = event.getX(actionIndex);
                mDownY = event.getY(actionIndex);
                mLastOffsetX = mOffsetX;
                mLastOffsetY = mOffsetY;
                break;
            // 非最后一根手指抬起，触发
            case MotionEvent.ACTION_POINTER_UP:
                int upIndex = event.getActionIndex();
                int pointId = event.getPointerId(upIndex);
                if (pointId == mCurrentIndex) {
                    if (upIndex == event.getPointerCount() - 1) {
                        upIndex = event.getPointerCount() - 2;
                    } else upIndex++;
                    mCurrentIndex = event.getPointerId(upIndex);
                    mDownX = event.getX(upIndex);
                    mDownY = event.getY(upIndex);
                    mLastOffsetX = mOffsetX;
                    mLastOffsetY = mOffsetY;
                }
                break;
        }
        return true;
    }
}
