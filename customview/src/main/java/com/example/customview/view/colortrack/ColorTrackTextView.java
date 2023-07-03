package com.example.customview.view.colortrack;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.customview.R;

public class ColorTrackTextView extends TextView {

    private Paint originPaint, changePaint;
    private float currentProgress = 0.0f;
    private String text;
    private Direction direction = Direction.DIRECTION_LEFT;

    public enum Direction {
        DIRECTION_LEFT, DIRECTION_RIGHT
    }


    public ColorTrackTextView(Context context) {
        this(context, null);
    }

    public ColorTrackTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorTrackTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ColorTrackTextView);
        int originColor = array.getColor(R.styleable.ColorTrackTextView_originColor, Color.BLACK);
        int changeColor = array.getColor(R.styleable.ColorTrackTextView_changeColor, Color.RED);
        originPaint = getPaint(originColor);
        changePaint = getPaint(changeColor);
        array.recycle();
    }

    private Paint getPaint(int color) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(color);
        paint.setTextSize(getTextSize());
        return paint;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        text = getText().toString();
        int middle = (int) (currentProgress * getWidth());
        if (!TextUtils.isEmpty(text)) {
            if (direction == Direction.DIRECTION_LEFT)
                drawTextToLeft(canvas, middle);
            if (direction == Direction.DIRECTION_RIGHT)
                drawTextToRight(canvas, middle);
        }
    }

    private void drawTextToLeft(Canvas canvas, int middle) {
        drawText(canvas, changePaint, 0, middle);
        drawText(canvas, originPaint, middle, getWidth());
    }

    private void drawTextToRight(Canvas canvas, int middle) {
        drawText(canvas, changePaint, getWidth() - middle, getWidth());
        drawText(canvas, originPaint, 0, getWidth() - middle);
    }

    private void drawText(Canvas canvas, Paint paint, int start, int end) {
        canvas.save();
        canvas.clipRect(start, 0, end, getHeight());
        Rect rect = new Rect();
        originPaint.getTextBounds(text, 0, text.length(), rect);
        Paint.FontMetricsInt metricsInt = originPaint.getFontMetricsInt();
        int baseLine = getHeight() / 2 - metricsInt.bottom / 2 - metricsInt.top / 2;
        canvas.drawText(text, ((getWidth() - rect.width()) / 2.0f), baseLine, paint);
        canvas.restore();
    }


    public void setOriginColor(int color) {
        this.originPaint.setColor(color);
    }

    public void setChangeColor(int color) {
        this.changePaint.setColor(color);
    }

    public void setProgress(float progress) {
        this.currentProgress = progress;
        invalidate();
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
