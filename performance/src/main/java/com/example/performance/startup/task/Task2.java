package com.example.performance.startup.task;

import android.content.Context;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;

import com.example.performance.startup.AndroidStartUp;
import com.example.performance.startup.StartUp;

import java.util.ArrayList;
import java.util.List;


public class Task2 extends AndroidStartUp<String> {

    static List<Class<? extends StartUp<?>>> depends;
    static {
        depends = new ArrayList<>();
        depends.add(Task1.class);
    }

    @Override
    public List<Class<? extends StartUp<?>>> dependencies() {
        return depends;
    }

    @Override
    public boolean callOnMainThread() {
        return true;
    }

    @Override
    public boolean waitOnMainThread() {
        return false;
    }

    @Override
    public String create(Context context) {
        String t = Looper.myLooper() == Looper.getMainLooper() ?
                "主线程" : "子线程";
        Log.d("TAG", "create: task2 create");
        SystemClock.sleep(200);
        return "Task2返回数据";
    }
}
