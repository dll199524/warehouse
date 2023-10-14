package com.example.common.mod.decorator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class WrapRecyclerView extends RecyclerView {

    private WrapRecyclerAdapter mAdapter;
    public WrapRecyclerView(@NonNull Context context) {
        super(context);
    }

    public WrapRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WrapRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setAdapter(@Nullable Adapter adapter) {
        mAdapter = new WrapRecyclerAdapter(adapter);
        super.setAdapter(mAdapter);
    }

    public void addHeadView(View view) {
        if (mAdapter == null) throw new RuntimeException("添加头部之前请先setAdapter");
        mAdapter.addHeadView(view);
    }

    public void removeHeadView(View view) {
        if (mAdapter == null) throw new RuntimeException("移除头部之前请先setAdapter");
        mAdapter.removeHeadView(view);
    }

    public void addFootView(View view) {
        if (mAdapter == null) throw new RuntimeException("添加头部之前请先setAdapter");
        mAdapter.addFootView(view);
    }

    public void removeFootView(View view) {
        if (mAdapter == null) throw new RuntimeException("添加头部之前请先setAdapter");
        mAdapter.removeFootView(view);
    }
}
