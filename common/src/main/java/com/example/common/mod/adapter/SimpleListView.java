package com.example.common.mod.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class SimpleListView extends ScrollView {

    private SimpleAdapter mAdapter;
    private LinearLayout mContainer;

    public SimpleListView(Context context) {
        this(context, null);
    }

    public SimpleListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContainer = new LinearLayout(context);
        mContainer.setOrientation(LinearLayout.VERTICAL);
        addView(mContainer, 0);
    }

    @Override
    public void addView(View child) {
        mContainer.addView(child);
    }

    public void setAdapter(SimpleAdapter mAdapter) {
        this.mAdapter = mAdapter;
        for (int i = 0; i < mAdapter.getCount(); i++) {
            View view = mAdapter.getView(i, mContainer);
            mContainer.addView(view);
        }
    }
}
