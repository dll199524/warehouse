package com.example.performance.startup;

import java.util.concurrent.Executor;

public interface Dispatcher {

    boolean callOnMainThread();
    boolean waitOnMainThread();
    void toWait();
    /**
     * 有父任务执行完毕
     * 计数器-1
     */
    void toNotify();
    Executor executor();
    int getThreadPriority();

}
