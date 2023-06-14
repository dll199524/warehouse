package com.example.performance.startup;


import android.content.Context;
import android.os.Looper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class StartUpManager {

    private CountDownLatch countDownLatch;
    StartUpCache startUpCache;
    Context context;
    List<StartUp<?>> startUpList;

    public StartUpManager(CountDownLatch countDownLatch,
                          Context context, List<StartUp<?>> startUpList) {
        this.countDownLatch = countDownLatch;
        this.context = context;
        this.startUpList = startUpList;
    }

    public StartUpManager start() {
        if (Looper.myLooper() != Looper.getMainLooper())
            throw new RuntimeException("请在主线程调用");
        startUpCache = TopologySort.sort(startUpList);
        for (StartUp<?> startUp : startUpCache.getResult()) {
            StartUpRunnable runnable = new StartUpRunnable(context, this, startUp);
            if (startUp.callOnMainThread()) {
                runnable.run();
            } else startUp.executor().execute(runnable);
        }
        return this;
    }

    public void await() {
        try {
            countDownLatch.await();
        } catch (Exception e) {e.printStackTrace();}
    }

    public void notifyChild(StartUp<?> startUp) {
        if (!startUp.callOnMainThread() && startUp.waitOnMainThread())
            countDownLatch.countDown();
        if (startUpCache.getStartupChildMap().containsKey(startUp.getClass())) {
            List<Class<? extends StartUp>> childClazzs = startUpCache.getStartupChildMap().get(startUp.getClass());
            for (Class<? extends StartUp> child : childClazzs) {
                StartUp<?> childStartUp = startUpCache.getStartupMap().get(child);
                childStartUp.toNotify();
            }
        }
    }

    public static class Builder {
        List<StartUp<?>> startUpList = new ArrayList<>();
        public Builder addStartUp(StartUp<?> start) {
            startUpList.add(start);
            return this;
        }
        public Builder addAllStartUp(List<StartUp<?>> list) {
            startUpList.addAll(list);
            return this;
        }
        public StartUpManager build(Context context) {
            AtomicInteger needAwaitCount = new AtomicInteger();
            for (StartUp<?> start : startUpList) {
                if (!start.callOnMainThread() && start.waitOnMainThread())
                    needAwaitCount.incrementAndGet();
            }
            CountDownLatch countDownLatch = new CountDownLatch(needAwaitCount.get());
            return new StartUpManager(countDownLatch, context, startUpList);
        }
    }

}
