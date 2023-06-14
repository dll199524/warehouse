package com.example.performance.startup;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TopologySort {

    public static StartUpCache sort(List<StartUp<?>> lists) {
        //入度，零度
        Map<Class<? extends StartUp>, Integer> inDegree = new HashMap<>();
        Deque<Class<? extends StartUp>> zeroDegree = new ArrayDeque<>();

        Map<Class<? extends StartUp>, StartUp<?>> startUpMap = new HashMap<>();
        Map<Class<? extends StartUp>, List<Class<? extends StartUp>>> startUpChildMap = new HashMap<>();

        for (StartUp<?> start : lists) {
            startUpMap.put(start.getClass(), start);
            int dependence = start.getDependcies();
            inDegree.put(start.getClass(), dependence);
            if (dependence == 0) zeroDegree.offer(start.getClass());
            else {
                //遍历本任务的依赖（父）任务列表
                for (Class<? extends StartUp<?>> parent : start.dependencies()) {
                    List<Class<? extends StartUp>> children = startUpChildMap.get(parent);
                    if (children == null) {
                        children = new ArrayList<>();
                        children.add(start.getClass());
                    }
                    startUpChildMap.put(parent, children);
                }
            }
        }

        List<StartUp<?>> result = new ArrayList<>();
        List<StartUp<?>> threads = new ArrayList<>();
        List<StartUp<?>> main = new ArrayList<>();

        while (!zeroDegree.isEmpty()) {
            Class clazz = zeroDegree.poll();
            StartUp<?> startUp = startUpMap.get(clazz);
            if (startUp.callOnMainThread())
                main.add(startUp);
            else threads.add(startUp);

            if (startUpChildMap.containsKey(clazz)) {
                List<Class<? extends StartUp>> childStartUp = startUpChildMap.get(clazz);
                for (Class<? extends StartUp> childCls : childStartUp) {
                    int num = inDegree.get(childCls);
                    inDegree.put(childCls, num - 1);
                    if (num == 0) zeroDegree.offer(childCls);
                }
            }
        }

        result.addAll(main);
        result.addAll(threads);
        return new StartUpCache(result, startUpMap, startUpChildMap);

    }

}
