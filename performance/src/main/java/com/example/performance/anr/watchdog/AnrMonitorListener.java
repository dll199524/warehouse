package com.example.performance.anr.watchdog;

public class AnrMonitorListener {
    // 发生anr的回调
    public interface AnrListener {
        void onAppNotResponding(AnrException e);
    }
    // 主线程发生{@link AnrMonitor#monitorInterval}阻塞时的回调
    public interface AnrInterceptor {
        long interrupt(long duration, AnrException e);
    }
    // Thread发生InterruptedException时的回调，用于关闭监听器
    public interface InterruptListener {
        void onInterrupted(InterruptedException e);
    }
}
