package com.example.performance.anr.log;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Looper;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.ArrayList;

public class StackTraceThread extends Thread {

    private static final String TAG = "StackTraceThread";
    boolean stop = false;
    Context mContext;
    private BroadcastReceiver receiver;
    private long lastTime = 0;
    private ArrayList<StackTraceInfo> list = new ArrayList<>(1024);

    public StackTraceThread(Context context) {
        this.mContext = context;
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(context);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                long startTime = intent.getIntExtra(LogPrinter.START_TIME, 0);
                long endTime = intent.getLongExtra(LogPrinter.END_TIME, 0);
                StackTraceInfo info = getStackTraceInfo(startTime, endTime);
                if (info != null) {
                    Log.d(TAG, "find block line");
                    Log.d(TAG, info.mLog);
                } else {
                    Log.d(TAG, "no block line find");
                }
            }
        };
        manager.registerReceiver(receiver, new IntentFilter(LogPrinter.BLOCK_EVENT));
    }

    private StackTraceInfo getStackTraceInfo(long start, long end) {
        for (StackTraceInfo info : list) {
            if (info.mTime >= start && info.mTime < end)
                return info;
        }
        return null;
    }

    @Override
    public void run() {
        super.run();
        while (!stop) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastTime > 500) {
                lastTime = currentTime;
                StackTraceInfo info = new StackTraceInfo();
                info.mTime = currentTime;
                info.mLog = getStackTraceInfoToString(Looper.getMainLooper().getThread().getStackTrace());
                list.add(info);
            }
            if (list.size() > 1024) list.remove(0);
        }
    }

    private String getStackTraceInfoToString(StackTraceElement[] stackTrace) {
        StringBuilder sb = new StringBuilder();
        if (stackTrace != null && stackTrace.length != 0) {
            for (int i = 0; i < stackTrace.length; i++) {
                sb.append("\tat");
                sb.append(stackTrace[i].toString());
                sb.append("\n");
            }
        }
        return sb.toString();
    }


    public void onStop () {
        stop = true;
        LocalBroadcastManager.getInstance(mContext).unregisterReceiver(receiver);
    }
}
