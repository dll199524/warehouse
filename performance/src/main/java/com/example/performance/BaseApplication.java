package com.example.performance;

import android.app.Application;
import android.content.Context;
import android.os.Looper;
import android.os.MessageQueue;
import android.os.StrictMode;
import android.util.Log;

import com.example.performance.anr.log.LogPrinter;
import com.example.performance.crash.ExceptionCrashHandler;
import com.example.performance.startup.StartUpManager;
import com.example.performance.startup.task.Task1;
import com.example.performance.startup.task.Task2;
import com.example.performance.startup.task.Task3;
import com.example.performance.startup.task.Task4;
import com.example.performance.startup.task.Task5;


public class BaseApplication extends Application {

    private static String TAG = "BaseApplication";
    private MessageQueue.IdleHandler idleHandler;

    public BaseApplication() {
        //method trace生成trace文件
//        Debug.startMethodTracing();
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

        long preTime = System.currentTimeMillis();
        new Task1().create(BaseApplication.this);
        new Task2().create(BaseApplication.this);
        new Task3().create(BaseApplication.this);
        new Task4().create(BaseApplication.this);
        new Task5().create(BaseApplication.this);
        long curTime = System.currentTimeMillis();
        Log.d(TAG, "onCreate: " + (curTime - preTime));

        new StartUpManager.Builder()
                .addStartUp(new Task5())
                .addStartUp(new Task4())
                .addStartUp(new Task3())
                .addStartUp(new Task2())
                .addStartUp(new Task1())
                .build(this)
                .start().await();
        Log.d(TAG, "onCreate: " + (System.currentTimeMillis() - curTime));

        //延迟加载主线程空闲的时候调用
//        idleHandler = new MessageQueue.IdleHandler() {
//            @Override
//            public boolean queueIdle() {
//                return false;
//            }
//        };
//
        //anr，crash监控
        ExceptionCrashHandler.getInstance().init(this);
        LogPrinter logPrinter = new LogPrinter(this);
        Looper.getMainLooper().setMessageLogging(logPrinter);

    }



    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }


}
