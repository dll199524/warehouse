package com.example.performance.anr.watchdog;

import android.os.Looper;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class AnrException extends Error {

    private final long duration;
    private AnrException(StackTrace.AnrThrowable stat, long duration) {
        super("应用程序没有响应" + duration + "ms" + stat);
        this.duration = duration;
    }

    @NonNull
    @Override
    public synchronized Throwable fillInStackTrace() {
        setStackTrace(new StackTraceElement[]{});
        return this;
    }

    static AnrException createThreadException(long duration, String filterStr, boolean logThreadsWithoutStackTrace) {
        Thread mainThread = Looper.getMainLooper().getThread();
        Map<Thread, StackTraceElement[]> maps = new TreeMap<>((o1, o2) -> {
            if (o1 == o2) return 0;
            if (o1 == mainThread) return 1;
            if (o2 == mainThread) return -1;
            return o2.getName().compareTo(o1.getName());
        });
        for (Map.Entry<Thread, StackTraceElement[]> entry : Thread.getAllStackTraces().entrySet()) {
            if (entry.getKey() == mainThread || (entry.getKey().getName().startsWith(filterStr == null ? "" : filterStr) && (logThreadsWithoutStackTrace || entry.getValue().length > 0)))
                maps.put(entry.getKey(), entry.getValue());
        }

        // 有时主线程在maps()中不返回，我们必须记录
        if (!maps.containsKey(mainThread)) {
            maps.put(mainThread, mainThread.getStackTrace());
        }
        StackTrace.AnrThrowable stat = null;
        for (Map.Entry<Thread, StackTraceElement[]> threadEntry : maps.entrySet()) {
            stat = new StackTrace(threadEntry.getKey(), threadEntry.getValue()).new AnrThrowable(stat);
        }
        return new AnrException(stat, duration);
    }

    static AnrException createThreadException(long duration) {
        Thread mainThread = Looper.getMainLooper().getThread();
        StackTraceElement[] mainStackTrace = mainThread.getStackTrace();
        return new AnrException(new StackTrace(mainThread, mainStackTrace).new AnrThrowable(null), duration);
    }


    private static class StackTrace implements Serializable {

        private Thread thread;
        private StackTraceElement[] stackTraceElements;
        private StackTrace(Thread thread, StackTraceElement[] stackTraceElements) {
            this.thread = thread;
            this.stackTraceElements = stackTraceElements;
        }
        private class AnrThrowable extends Throwable {
            private AnrThrowable(AnrThrowable other) {
                super(thread.getName() + "(state=" + thread.getState() + ")", other);
            }
            @NonNull
            @Override
            public synchronized Throwable fillInStackTrace() {
                setStackTrace(stackTraceElements);
                return this;
            }
        }

    }

}
