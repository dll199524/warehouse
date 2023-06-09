package com.example.performance.startup;

import android.content.Context;

import java.util.List;

public interface StartUp<T> extends Dispather{
    T create(Context context);
    List<StartUp<?>> dependencies();
    int getDependcies();
}
