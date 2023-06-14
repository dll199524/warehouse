package com.example.performance.startup.task;

import android.content.Context;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;

import com.example.performance.startup.AndroidStartUp;
import com.example.performance.startup.StartUp;

import java.util.List;

public class Task1 extends AndroidStartUp<String> {

    @Override
    public List<Class<? extends StartUp<?>>> dependencies() {
        return super.dependencies();
    }

    @Override
    public boolean callOnMainThread() {
        return false;
    }

    @Override
    public boolean waitOnMainThread() {
        return false;
    }

    @Override
    public String create(Context context) {
        String t = Looper.myLooper() == Looper.getMainLooper() ?
                "主线程" : "子线程";
        Log.d("TAG", "create: task1 create");
        SystemClock.sleep(300);
        return "Task1返回数据";
    }
}
