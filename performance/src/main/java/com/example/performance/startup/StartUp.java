package com.example.performance.startup;

import android.content.Context;

import java.util.List;

public interface StartUp<T> extends Dispather{
    T create(Context context);
    /**
     * 本任务依赖哪些任务
     */
    List<Class<? extends StartUp<?>>> dependencies();
    int getDependcies();
}
