package com.example.performance.mystratup;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;

public class MainExecutor implements Executor {

    private final BlockingQueue<Runnable> queue;

    public MainExecutor() {
        queue = new LinkedBlockingDeque<>();
    }

    @Override
    public void execute(Runnable command) {
        queue.offer(command);
    }

    public Runnable tack() throws InterruptedException {
        return queue.take();
    }
}
