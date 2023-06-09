package com.example.performance.startup;

import java.util.concurrent.Executor;

public interface Dispather {

    boolean callOnMainThread();
    boolean waitOnMainThread();
    void toWait();
    void toNotify();
    Executor executor();
    int getThreadPriority();

}
