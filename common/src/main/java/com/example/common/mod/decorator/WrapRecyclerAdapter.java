package com.example.common.mod.decorator;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 装饰后的Adapter,对原来的Adapter进行功能扩展,支持头部和尾部的添加
 */
public class WrapRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private RecyclerView.Adapter mRealAdapter;
    private final List<View> mHeadViews;
    private final List<View> mFootViews;

    public WrapRecyclerAdapter(RecyclerView.Adapter<?> adapter) {
        mRealAdapter = adapter;
        mRealAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                notifyDataSetChanged();
            }
        });
        mHeadViews = new ArrayList<>();
        mFootViews = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int position = viewType;
        int headCount = getHeadSize();
        if (position < headCount) return createHeadFootViewHolder(mHeadViews.get(position));
        int realPosition = position - headCount;
        int realCount = mRealAdapter.getItemCount();
        if (mRealAdapter != null) {
            if (realPosition < realCount) {
                return mRealAdapter.onCreateViewHolder(parent, mRealAdapter.getItemViewType(position));
            }
        }
        return createHeadFootViewHolder(mFootViews.get(position));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int headCount = getHeadSize();
        if (position < headCount) return;
        int realPosition = position - headCount;
        int realCount = mRealAdapter.getItemCount();
        if (mRealAdapter != null) {
            if (realPosition < realCount) {
                mRealAdapter.onBindViewHolder(holder, realPosition);
            }
        }
    }

    public RecyclerView.ViewHolder createHeadFootViewHolder(View view) {return new RecyclerView.ViewHolder(view){};}

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        int realCount = mRealAdapter != null ? mRealAdapter.getItemCount() : 0;
        return realCount + getHeadSize() + getFootSize();
    }

    protected void addHeadView(View view) {
        if (mHeadViews.contains(view)) throw new RuntimeException("同一个头部不能重复添加");
        mHeadViews.add(view);
        notifyDataSetChanged();
    }

    protected void removeHeadView(View view) {
        if (mHeadViews.contains(view)) {
            mHeadViews.remove(view);
            notifyDataSetChanged();
        }
    }

    protected void addFootView(View view) {
        if (mFootViews.contains(view)) throw new RuntimeException("同一个尾部不能重复添加");
        mFootViews.add(view);
        notifyDataSetChanged();
    }

    protected void removeFootView(View view) {
        if (mFootViews.contains(view)) {
            mFootViews.remove(view);
            notifyDataSetChanged();
        }
    }

    public int getHeadSize() {return mHeadViews.size();}
    public int getFootSize() {return mFootViews.size();}
}
