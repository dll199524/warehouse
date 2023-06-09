package com.example.performance.startup;

import java.util.List;
import java.util.Map;

public class StartUpCache {
    List<StartUp<?>> result;
    Map<Class<? extends StartUp<?>>, StartUp<?>> startupMap;
    Map<Class<? extends StartUp<?>>, List<StartUp<?>>> startupChildMap;

    public StartUpCache(List<StartUp<?>> result, Map<Class<? extends StartUp<?>>,
            StartUp<?>> startupMap, Map<Class<? extends StartUp<?>>, List<StartUp<?>>> startupChildMap) {
        this.result = result;
        this.startupMap = startupMap;
        this.startupChildMap = startupChildMap;
    }

    public List<StartUp<?>> getResult() {
        return result;
    }

    public void setResult(List<StartUp<?>> result) {
        this.result = result;
    }

    public Map<Class<? extends StartUp<?>>, StartUp<?>> getStartupMap() {
        return startupMap;
    }

    public void setStartupMap(Map<Class<? extends StartUp<?>>, StartUp<?>> startupMap) {
        this.startupMap = startupMap;
    }

    public Map<Class<? extends StartUp<?>>, List<StartUp<?>>> getStartupChildMap() {
        return startupChildMap;
    }

    public void setStartupChildMap(Map<Class<? extends StartUp<?>>, List<StartUp<?>>> startupChildMap) {
        this.startupChildMap = startupChildMap;
    }
}
