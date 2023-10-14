package com.example.common;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.common.mod.build.DefaultNavigationBar;
import com.example.common.mod.decorator.WrapRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewGroup mViewGroupRoot;

    private WrapRecyclerView mRecyclerView;
    private List<String> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewGroupRoot = findViewById(R.id.view_root);
        mRecyclerView = findViewById(R.id.wrap_recycler);
        mData = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mData.add(String.format("position%d", i));
        }
        testBuild();
        testDecorator();


    }


    private void testBuild() {
        DefaultNavigationBar navigationBar = new DefaultNavigationBar.Builder(this, mViewGroupRoot)
                .setLeftText("返回", v -> finish())
                .setLeftTextVisible(View.VISIBLE)
                .build();
    }

    private void testDecorator() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new MyAdapter());
        View headView = LayoutInflater.from(this).inflate(R.layout.layout_header, mRecyclerView, false);
        View footView = LayoutInflater.from(this).inflate(R.layout.layout_footer, mRecyclerView, false);
        mRecyclerView.addHeadView(headView);
        mRecyclerView.addFootView(footView);
    }


    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.mTv.setText(mData.get(position));
            holder.mTv.setOnClickListener(v -> {
                mData.remove(position);
                notifyDataSetChanged();
            });
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView mTv;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                mTv = findViewById(R.id.tv);
            }
        }
    }

    @Override
    public void finish() {
        super.finish();

    }
}