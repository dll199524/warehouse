package com.example.customview.utils;

import android.graphics.Paint;

public class PaintUtils {

    public static Paint initPaint(Paint paint, int color) {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(color);
        return paint;
    }
}
