package com.example.common.mod.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.common.R;

import java.util.List;

public class SimpleAdapter extends BaseAdapter {

    private List<String> mData;
    public SimpleAdapter(List<String> data) {this.mData = data;}

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public View getView(int position, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item, parent, false);
        TextView textView = view.findViewById(R.id.tv);
        textView.setText(mData.get(position));
        return view;
    }
}
