package com.example.customview.view.floatview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.customview.R;
import com.example.customview.utils.DensityUtil;

public class DraggableFlagView extends View {

    private static final String TAG = DraggableFlagView.class.getSimpleName();
    private Paint paint, textPaint;
    private int radiusColor;
    private Paint.FontMetrics textFontMetrics;


    public DraggableFlagView(Context context) {
        this(context, null);
    }

    public DraggableFlagView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DraggableFlagView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.DraggableFlagView);
        radiusColor = array.getColor(R.styleable.DraggableFlagView_radiusColor, Color.RED);
        array.recycle();

        setBackgroundColor(Color.TRANSPARENT);
        paint = new Paint();
        paint.setDither(true);
        paint.setAntiAlias(true);
        paint.setColor(radiusColor);

        textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(DensityUtil.sp2px(context, 10));
        textPaint.setTextAlign(Paint.Align.CENTER);
        textFontMetrics = textPaint.getFontMetrics();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
