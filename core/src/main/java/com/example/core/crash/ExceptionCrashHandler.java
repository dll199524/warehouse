package com.example.core.crash;

import android.content.Context;

import androidx.annotation.NonNull;

public class ExceptionCrashHandler implements Thread.UncaughtExceptionHandler {

    Context mContext;

    public ExceptionCrashHandler(Context context) {

    }

    @Override
    public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {

    }
}
