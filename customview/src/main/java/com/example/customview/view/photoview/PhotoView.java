package com.example.customview.view.photoview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.OverScroller;

import androidx.annotation.Nullable;

import com.example.customview.R;

public class PhotoView extends View {

    private static final String TAG = "PhotoView";
    private Paint mPaint;
    private Bitmap mBitmap;
    private GestureDetector mGestureDetector;
    private ScaleGestureDetector mScaleGestureDetector;     //双指放大缩小
    private float mStartX;
    private float mStartY;
    private float mSmallScale;
    private float mBigScale;
    private float mCurrentScale;
    private final float OVER_SCALE_FACTOR = 1.5f;
    private boolean isEnlarge = false;
    private boolean isScale = false;
    private FlingRunner mFilingRunner;
    private float mOffsetX;
    private float mOffsetY;
    private OverScroller mOverScroller;     //处理回弹
    private ObjectAnimator mScaleAnimator;


    public PhotoView(Context context) {
        this(context, null);
    }

    public PhotoView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PhotoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.photo);
        mGestureDetector = new GestureDetector(context, new PhotoGestureDetector());
        mOverScroller = new OverScroller(context);
        mScaleGestureDetector = new ScaleGestureDetector(context, new PhotoScaleGestureDetector());
        mFilingRunner = new FlingRunner();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = mScaleGestureDetector.onTouchEvent(event);
        if (!mScaleGestureDetector.isInProgress())
            result = mGestureDetector.onTouchEvent(event);
        return result;
    }

    // onMeasure --> onSizeChanged
    // 每次改变尺寸时也会调用
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mStartX = (getWidth() - mBitmap.getWidth()) / 2.0f;
        mStartY = (getHeight() - mBitmap.getHeight()) / 2.0f;
        //图片横向
        if ((mBitmap.getWidth() / mBitmap.getHeight()) > (getWidth() / getHeight())) {
            mSmallScale = (float) mBitmap.getWidth() / getWidth();
            mBigScale = (float) mBitmap.getWidth() / getWidth() * OVER_SCALE_FACTOR;
            //纵向
        } else {
            mSmallScale = (float) mBitmap.getHeight() / getHeight();
            mBigScale = (float) mBitmap.getHeight() / getHeight() * OVER_SCALE_FACTOR;
        }
        mCurrentScale = mSmallScale;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float scaleFraction = (mCurrentScale - mSmallScale) / (mBigScale - mCurrentScale);
        canvas.translate(mOffsetX * scaleFraction, mOffsetY * scaleFraction);
        canvas.scale(mCurrentScale, mCurrentScale, getWidth() / 2f, getHeight() / 2f);
        canvas.drawBitmap(mBitmap, mStartX, mStartY, mPaint);
    }


    class PhotoGestureDetector extends GestureDetector.SimpleOnGestureListener {

        //滑动
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            //distance = x1 - x2 为负数
            if (isEnlarge) {
                mOffsetX = -distanceX;
                mOffsetY = -distanceY;
                fixOffsets();
                invalidate();
            }
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        //抛掷
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (isEnlarge) {
                // 只会处理一次
                mOverScroller.fling((int) mOffsetX, (int) mOffsetY, (int) velocityX, (int) velocityY,
                        -(int) (mBitmap.getWidth() * mBigScale - getWidth()) / 2,
                        (int) (mBitmap.getWidth() * mBigScale - getWidth()) / 2,
                        -(int) (mBitmap.getHeight() * mBigScale - getHeight()) / 2,
                        (int) (mBitmap.getHeight() * mBigScale - getHeight()) / 2, 600, 600);
                postOnAnimation(mFilingRunner);
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            isEnlarge = !isEnlarge;
            if (isEnlarge) {
                //手指按下的区域进行放大
                mOffsetX = (e.getX() - getWidth() / 2f) - (e.getX() - getWidth() / 2f) * mBigScale / mSmallScale;
                mOffsetX = (e.getY() - getHeight() / 2f) - (e.getY() - getHeight() / 2f) * mBigScale / mSmallScale;
                fixOffsets();
                getScaleAnimator().start();
            } else getScaleAnimator().reverse();
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
    }

    class PhotoScaleGestureDetector implements ScaleGestureDetector.OnScaleGestureListener {

        float initScale;

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            if ((mCurrentScale > mSmallScale && !isEnlarge)
                    || (mCurrentScale == mSmallScale && isEnlarge)) {
                isEnlarge = !isEnlarge;
            }
            mCurrentScale = initScale * detector.getScaleFactor();
            isScale = true;
            invalidate();
            return false;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            initScale = mCurrentScale;
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {

        }
    }

    private ObjectAnimator getScaleAnimator() {
        if (mScaleAnimator == null) {
            mScaleAnimator = ObjectAnimator.ofFloat(this, "currentScale", 0);
        }
        if (isScale) {
            mScaleAnimator.setFloatValues(mSmallScale, mCurrentScale);
            isScale = false;
        } else {
            mScaleAnimator.setFloatValues(mSmallScale, mBigScale);
        }
        return mScaleAnimator;
    }

    public void setCurrentScale(float currentScale) {
        this.mCurrentScale = currentScale;
    }

    private void fixOffsets() {
        mOffsetX = Math.min(mOffsetX, (mBitmap.getWidth() * mBigScale - getWidth()) / 2);
        mOffsetX = Math.max(mOffsetX, -(mBitmap.getWidth() * mBigScale - getWidth()) / 2);
        mOffsetY = Math.min(mOffsetY, (mBitmap.getHeight() * mBigScale - getHeight()) / 2);
        mOffsetY = Math.max(mOffsetY, -(mBitmap.getHeight() * mBigScale - getHeight()) / 2);
    }

    class FlingRunner implements Runnable {

        @Override
        public void run() {
            if (mOverScroller.computeScrollOffset()) {
                mOffsetX = mOverScroller.getCurrX();
                mOffsetY = mOverScroller.getCurrY();
                invalidate();
                postOnAnimation(this);
            }
        }
    }

}
