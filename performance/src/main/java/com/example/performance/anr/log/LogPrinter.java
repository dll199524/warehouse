package com.example.performance.anr.log;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Printer;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

//https://github.com/fengcunhan/BlockCanary
public class LogPrinter implements Printer {

    public static final String BLOCK_EVENT = "com.example.performance.logBroadcast";
    public static final String START_TIME = "block_start_time";
    public static final String END_TIME = "block_end_time";
    long startTime, endTime;
    private Context mContext;

    public LogPrinter(Context context) {
        this.mContext = context;
        StackTraceThread thread = new StackTraceThread(mContext);
        thread.run();
    }

    @Override
    public void println(String x) {
        switch (isStart(x)) {
            case 0:
                startTime = System.currentTimeMillis();
                break;
            case 1:
                endTime = System.currentTimeMillis();
                if (isBlock(endTime, startTime))
                    notifyEventBlock();
                break;
            default:
                break;
        }
    }

    private void notifyEventBlock() {
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(mContext);
        Intent in = new Intent(BLOCK_EVENT);
        in.putExtra(START_TIME, startTime);
        in.putExtra(END_TIME, endTime);
        localBroadcastManager.sendBroadcast(in);
    }

    //Looper loop() 中有此打印
    private int isStart(String x) {
        if (!TextUtils.isEmpty(x)) {
            if (x.startsWith(">>>>> Dispatching to "))
                return 0;
            else if (x.startsWith("<<<<< Finished to "))
                return 1;
        }
        return -1;
    }

    private boolean isBlock(long endTime, long startTime) {
        return endTime - startTime > 500;
    }
}
