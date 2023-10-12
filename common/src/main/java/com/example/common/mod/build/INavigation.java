package com.example.common.mod.build;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public interface INavigation {

    View createNavigationBar(Context context, int layoutId, ViewGroup parent);
    void attachParent(View view, ViewGroup parent);
    void addParams();

}
