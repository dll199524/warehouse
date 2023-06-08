package com.example.performance;

import android.app.Application;
import android.content.Context;
import android.os.Debug;
import android.os.StrictMode;


public class BaseApplication extends Application {

    public BaseApplication() {
        //method trace生成trace文件
        Debug.startMethodTracing();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //StrictMode最常用于捕获应用程序主线程上的意外磁盘或网络访问。帮助我们让磁盘和网络操作远离主线程，可以使应用程序更加平滑、响应更快。
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()   //违规打印日志
                .penaltyDeath() //违规崩溃
                .build());
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}
