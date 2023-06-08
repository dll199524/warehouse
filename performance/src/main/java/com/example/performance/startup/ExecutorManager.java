package com.example.performance.startup;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorManager {

    public static ThreadPoolExecutor  cpuExecutor;
    public static ExecutorService ioExecutor;
    public static Executor mainExecutor;

    private static int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static int CORE_THREAD_COUNT = Math.max(2, Math.min(CPU_COUNT - 1, 5));
    private static int MAX_THREAD_COUNT = CORE_THREAD_COUNT;
    private static long KEEP_ALIVE_TIME = 5L;

    static {
        cpuExecutor = new ThreadPoolExecutor(CORE_THREAD_COUNT, MAX_THREAD_COUNT,
                KEEP_ALIVE_TIME, TimeUnit.SECONDS, new LinkedBlockingDeque<>(),
                Executors.defaultThreadFactory());
        ioExecutor = Executors.newCachedThreadPool(Executors.defaultThreadFactory());
        mainExecutor = new Executor() {
            Handler handler = new Handler(Looper.getMainLooper());
            @Override
            public void execute(Runnable command) {
                handler.post(command);
            }
        };
    }
}
