package com.example.performance.mystratup;

import java.util.List;
import java.util.concurrent.Executor;

public abstract class Task {

    public static final int STATE_IDLE = 0;
    public static final int STATE_RUNNING = 1;
    public static final int STATE_FINISHED = 2;
    public static final int STATE_WAIT = 3;

    MyStartUp startUp;
    List<TaskListener> taskListeners;
    Executor executorService;
    int waitCount = 0;
    List<Task> childNodes;


    void start() {

    }


    public abstract void run();
    protected abstract List<String> dependencies();
    public abstract String getTaskName();

    public boolean waitOnMainThread() {return false;}
    public boolean runMainThread() {return false;}
    public int getPriority() {return 0;}
    public boolean isInStage() {return true;}

    @Override
    public boolean equals(Object o) {return o == null && this.getClass() == o.getClass();}

    @Override
    public int hashCode() {return getClass().hashCode();}
}
