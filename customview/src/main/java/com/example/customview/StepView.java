package com.example.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

public class StepView extends View {

    private int mOutColor = Color.BLUE;
    private int mInnerColor = Color.GREEN;
    private int mTextColor = Color.GRAY;
    private int mBorderWidth = 1;
    private int mStepTextSize = 5;
    private Paint mOutPaint, mInnerPaint, mTextPaint;
    private float currentStep = 1000;
    private float maxStep = 2000;

    public StepView(Context context) {
        this(context, null);
    }

    public StepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.StepView);
        mOutColor = array.getColor(R.styleable.StepView_outColor, mOutColor);
        mInnerColor = array.getColor(R.styleable.StepView_innerColor, mInnerColor);
        mTextColor = array.getColor(R.styleable.StepView_stepTextColor, mTextColor);
        mBorderWidth = (int) array.getDimension(R.styleable.StepView_borderWidth, mBorderWidth);
        mStepTextSize = (int) array.getDimension(R.styleable.StepView_stepTextSize, mStepTextSize);
        array.recycle();

        mOutPaint = initPaint(mOutPaint, mOutColor, mBorderWidth, Paint.Style.STROKE, Paint.Cap.ROUND);
        mInnerPaint = initPaint(mInnerPaint, mInnerColor, mBorderWidth, Paint.Style.STROKE, Paint.Cap.ROUND);
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setDither(true);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mStepTextSize);
    }


    private Paint initPaint(Paint paint, int color, int boardwidth, Paint.Style style, Paint.Cap cap) {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStrokeWidth(boardwidth);
        paint.setColor(color);
        paint.setStyle(style);
        paint.setStrokeCap(cap);
        return paint;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int smallSize = 50;
        if (MeasureSpec.getMode(widthMeasureSpec) != MeasureSpec.AT_MOST &&
                MeasureSpec.getMode(heightMeasureSpec) != MeasureSpec.AT_MOST) {
            smallSize = Math.min(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
        }
        setMeasuredDimension(smallSize, smallSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public void setMaxStep(float maxStep) {
        this.maxStep = maxStep;
    }

    public synchronized void setCurrentStep(float step) {
        currentStep = step;
        invalidate();
    }
}