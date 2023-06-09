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
        Map<Class<? extends StartUp>, List<StartUp<?>>> startUpChildMap = new HashMap<>();

        for (StartUp<?> start : lists) {
            startUpMap.put(start.getClass(), start);
            int dependence = start.getDependcies();
            inDegree.put(start.getClass(), dependence);
            if (dependence == 0) zeroDegree.offer(start.getClass());
            else {

            }
        }

        return null;

    }

}
