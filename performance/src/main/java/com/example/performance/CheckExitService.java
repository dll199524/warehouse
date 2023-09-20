package com.example.performance;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.List;

public class CheckExitService extends Service {

    private static final String mPackageName = "com.example.performance";

    @Override
    public void onCreate() {
        super.onCreate();
        showToast("检测开启");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = am.getRunningAppProcesses();
        for (int i = 0; i < runningAppProcesses.size(); i++) {
            if (mPackageName.equals(runningAppProcesses.get(i).processName))
                showToast("app在运行中");
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        showToast("app退出了");
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
