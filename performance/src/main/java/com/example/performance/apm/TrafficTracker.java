package com.example.performance.apm;

import android.app.Activity;
import android.net.TrafficStats;
import android.os.Process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TrafficTracker extends BaseITracker {

    private static volatile TrafficTracker instance;
    private static int sequence;
    private long currentStats;
    private List<TrafficListener> trafficListeners = new ArrayList<>(8);
    private HashMap<Activity, Traffic> maps = new HashMap<>();


    public static TrafficTracker getInstance() {
        if (instance == null) {
            synchronized (TrafficTracker.class) {
                if (instance == null) instance = new TrafficTracker();
            }
        }
        return instance;
    }

    private TrafficTracker() {}
    private interface TrafficListener {void getTrafficStats(Activity activity, long val);}
    private void addTrafficListener(TrafficListener listener) {trafficListeners.add(listener);}
    private void removeTrafficListener(TrafficListener listener) {trafficListeners.remove(listener);}

    private void markActivityStart(Activity activity) {
        if (maps.get(activity) == null) {
            Traffic traffic = new Traffic();
            traffic.activity = activity;
            traffic.sequence = sequence++;
            traffic.trafficCost = 0;
            traffic.activityName = activity.getClass().getSimpleName();
            maps.put(activity, traffic);
        }
        currentStats = TrafficStats.getUidRxBytes(Process.myUid());
    }

    public void markActivityPause(Activity activity) {
        Traffic traffic = maps.get(activity);
        if (traffic != null) {
            traffic.trafficCost = TrafficStats.getUidRxBytes(Process.myUid()) - currentStats;
        }
    }


    public void markActivityDestroy(Activity activity) {
        Traffic traffic = maps.get(activity);
        if (traffic != null) {
            for (TrafficListener listener : trafficListeners) {
                listener.getTrafficStats(activity, traffic.trafficCost);
                maps.remove(activity);
            }
        }
        traffic.activity = null;
    }

}
