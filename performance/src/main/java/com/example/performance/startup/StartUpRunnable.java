package com.example.performance.startup;

import android.content.Context;
import android.os.Process;

public class StartUpRunnable implements Runnable {

    private Context context;
    private StartUpManager startUpManager;
    private StartUp<?> startUp;

    public StartUpRunnable(Context context, StartUpManager startUpManager, StartUp<?> startUp) {
        this.context = context;
        this.startUpManager = startUpManager;
        this.startUp = startUp;
    }

    @Override
    public void run() {
        Process.setThreadPriority(startUp.getThreadPriority());
        startUp.toWait();
        Object result = startUp.create(context);
        StartUpManagerCache.getInstance().saveInitialized(startUp.getClass(),
                new Result(result));
        startUpManager.notifyChild(startUp);
    }

}
