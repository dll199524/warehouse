package com.example.performance.startup;

import java.util.concurrent.ConcurrentHashMap;

public class StartUpManagerCache {
    private ConcurrentHashMap<Class<? extends StartUp>, Result> startUpResult =
            new ConcurrentHashMap<>();
    private volatile static StartUpManagerCache instance;

    private StartUpManagerCache(){}
    public static StartUpManagerCache getInstance() {
        if (instance == null) {
            synchronized (StartUpManagerCache.class) {
                if (instance == null) instance = new StartUpManagerCache();
            }
        }
        return instance;
    }

    public boolean hasInitialized(Class<? extends StartUp> clazz) {
        return startUpResult.containsKey(clazz);
    }

    public void saveInitialized(Class<? extends StartUp> clazz, Result result) {
        startUpResult.put(clazz, result);
    }

    public Result obtainResult(Class<? extends StartUp> clazz) {
        return startUpResult.get(clazz);
    }

    public void remove(Class<? extends StartUp> clazz) {
        startUpResult.remove(clazz);
    }

    public void clear() {startUpResult.clear();}



}
