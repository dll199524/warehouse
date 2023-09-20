package com.example.performance.anr.watchdog;

import android.content.Context;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;


//https://github.com/SalomonBrys/ANR-WatchDog/
//https://github.com/bytebeats/Anr-Monitor/tree/master
//https://github.com/Jack6Wolf/AnrMonitor
public class AnrMonitor extends Thread implements LifecycleEventObserver {

    private static int DEFAULT_ANR_TIMEOUT = 5000;
    private final int monitorInterval;
    private String filterStr = "";
    //是否需要记录没有堆栈信息的线程
    private boolean logThreadsWithoutStackTrace = false;
    private boolean isDebug = false;
    private boolean isStop = false;

    private volatile long tick = 0;
    private volatile boolean reported = false;
    private Context mContext;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    private AnrMonitorListener.AnrListener DEFAULT_ANR_LISTENER = new AnrMonitorListener.AnrListener() {
        @Override
        public void onAppNotResponding(AnrException e) {
            throw e;
        }
    };
    private AnrMonitorListener.AnrInterceptor DEFAULT_ANR_INTERCEPTOR = new AnrMonitorListener.AnrInterceptor() {
        @Override
        public long interrupt(long duration, AnrException e) {
            vibrator();
            return 0;
        }
    };
    private AnrMonitorListener.InterruptListener DEFAULT_ANR_INTERRUPT = new AnrMonitorListener.InterruptListener() {
        @Override
        public void onInterrupted(InterruptedException e) {
            Log.e("AnrMonitor", "Interrupted: " + e.getMessage());
        }
    };

    private AnrMonitorListener.AnrListener anrListener = DEFAULT_ANR_LISTENER;
    private AnrMonitorListener.AnrInterceptor anrInterceptor = DEFAULT_ANR_INTERCEPTOR;
    private AnrMonitorListener.InterruptListener interruptListener = DEFAULT_ANR_INTERRUPT;

    private final Runnable ticker = () -> {
        tick = 0;
        reported = false;
    };

    public AnrMonitor(Context context) {
        this(context, DEFAULT_ANR_TIMEOUT);
    }

    private AnrMonitor(Context context, int timeOut) {
        super();
        this.mContext = context;
        this.monitorInterval = timeOut;
    }


    @Override
    public void run() {
        setName("AnrMonitor");
        long interval = monitorInterval;
        while (!isStop && !isInterrupted()) {
            boolean needPost = tick == 0;
            tick += interval;
            if (needPost) mHandler.post(ticker);
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                interruptListener.onInterrupted(e);
                return;
            }
            long duration = tick;
            while (duration != 0 && !reported) {
                if (!isDebug && (Debug.isDebuggerConnected() || Debug.waitingForDebugger())) {
                    Log.w("AnrMonitor", "检测到发生阻塞，默认是忽略它的，但是可以使用setIgnoreDebugger(true)不忽略");
                    reported = true;
                    continue;
                }
                interval = anrInterceptor.interrupt(duration, AnrException.createThreadException(duration, "", false));
                if (interval > 0) continue;

                AnrException anrException;
                if (filterStr != null) anrException = AnrException.createThreadException(duration, filterStr, logThreadsWithoutStackTrace);
                else anrException = AnrException.createThreadException(duration);
                anrListener.onAppNotResponding(anrException);
                interval = monitorInterval;
                reported = true;
            }

        }
    }

    @Override
    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {

    }

    public AnrMonitor setAnrListener(AnrMonitorListener.AnrListener anrListener) {
        if (anrListener == null) this.anrListener = DEFAULT_ANR_LISTENER;
        else this.anrListener = anrListener;
        return this;
    }

    public AnrMonitor setAnrInterceptor(AnrMonitorListener.AnrInterceptor anrInterceptor) {
        if (anrInterceptor == null) this.anrInterceptor = DEFAULT_ANR_INTERCEPTOR;
        else this.anrInterceptor = anrInterceptor;
        return this;
    }

    public AnrMonitor setInterruptListener(AnrMonitorListener.InterruptListener interruptListener) {
        if (interruptListener == null) interruptListener = DEFAULT_ANR_INTERRUPT;
        else this.interruptListener = interruptListener;
        return this;
    }

    public AnrMonitor setReportThreadNameFilter(String filterStr) {
        if (filterStr == null) {
            this.filterStr = "";
        }
        this.filterStr = filterStr;
        return this;
    }

    //设置只记录主线程
    public AnrMonitor setReportMainThreadOnly() {
        this.filterStr = null;
        return this;
    }

    //设置将记录所有线程
    public AnrMonitor setReportAllThreads() {
        this.filterStr = "";
        return this;
    }

    public void setLogThreadsWithoutStackTrace(boolean logThreadsWithoutStackTrace) {
        this.logThreadsWithoutStackTrace = logThreadsWithoutStackTrace;
    }

    public AnrMonitor setDebug(boolean debug) {
        isDebug = debug;
        return this;
    }

    public int getMonitorInterval() {
        return monitorInterval;
    }

    public void stopMonitor() {
        mHandler.removeCallbacksAndMessages(null);
        isStop = true;
        this.interrupt();
    }


    //震动提醒
    public void vibrator() {
        Vibrator vibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
        long[] patter = {1, 500, 100, 500};
        vibrator.vibrate(patter, -1);
    }
}
