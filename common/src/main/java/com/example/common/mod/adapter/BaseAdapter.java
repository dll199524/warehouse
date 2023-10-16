package com.example.common.mod.adapter;

import android.view.View;
import android.view.ViewGroup;

public abstract class BaseAdapter {

    public abstract int getCount();
    public abstract View getView(int position, ViewGroup parent);

}
