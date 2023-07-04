package com.example.customview.view.floatview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class DraggableFlagView extends View {

    private static final String TAG = DraggableFlagView.class.getSimpleName();

    public DraggableFlagView(Context context) {
        super(context);
    }

    public DraggableFlagView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DraggableFlagView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
