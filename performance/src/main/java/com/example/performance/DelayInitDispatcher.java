package com.example.performance;

import android.os.Looper;
import android.os.MessageQueue;

import java.util.LinkedList;
import java.util.Queue;

public class DelayInitDispatcher<T> {

    private Queue<T> mDelayTasks = new LinkedList<>();
    private MessageQueue.IdleHandler idleHandler = new MessageQueue.IdleHandler() {
        @Override
        public boolean queueIdle() {
            T task = mDelayTasks.poll();
            new DispatcherRunnable<>(task).run();
            return !mDelayTasks.isEmpty();
        }
    };

    public void addTask(T task) {
        mDelayTasks.add(task);
    }

    public void start() {
        Looper.myQueue().addIdleHandler(idleHandler);
    }


    static class DispatcherRunnable<T> implements Runnable {
        T task;
        public DispatcherRunnable(T task) {
            this.task = task;
        }
        @Override
        public void run() {

        }
    }
}
