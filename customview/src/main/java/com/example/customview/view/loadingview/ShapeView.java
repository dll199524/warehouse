package com.example.customview.view.loadingview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.customview.R;

public class ShapeView extends View {

    private Paint mPaint;
    private Path mPath;
    private Shape mCurrentShape = Shape.Circle;

    public ShapeView(Context context) {
        this(context, null);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int size = Math.min(width, height);
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (mCurrentShape) {
            case Circle:
                mPaint.setColor(ContextCompat.getColor(getContext(), R.color.circle));
                int center = getMeasuredWidth() / 2;
                canvas.drawCircle(center, center, center, mPaint);
                break;
            case Square:
                mPaint.setColor(ContextCompat.getColor(getContext(), R.color.square));
                canvas.drawRect(0, 0, getRight(), getBottom(), mPaint);
                break;
            case Triangle:
                mPaint.setColor(ContextCompat.getColor(getContext(), R.color.triangle));
                if (mPath == null) {
                    mPath = new Path();
                    mPath.moveTo(getWidth() / 2, 0);
                    mPath.lineTo(0, (float) (getWidth() / 2 * Math.sqrt(3)));
                    mPath.lineTo(getWidth(), (float) (getWidth() / 2 * Math.sqrt(3)));
                    mPath.close();
                }
                canvas.drawPath(mPath, mPaint);
                break;
            default:
                break;
        }
    }

    public enum Shape {Circle, Square, Triangle}

    public void exchange() {
        switch (mCurrentShape) {
            case Circle:
                mCurrentShape = Shape.Square;
                break;
            case Square:
                mCurrentShape = Shape.Triangle;
                break;
            case Triangle:
                mCurrentShape = Shape.Circle;
                break;
            default:
                break;
        }
        invalidate();
    }

    public Shape getCurrentShape() {return mCurrentShape;}
}
