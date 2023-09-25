package com.example.common.mod;

import android.app.Activity;

import java.util.Iterator;
import java.util.Stack;

public class ActivityManager {

    private static volatile ActivityManager instance;
    private Stack<Activity> activityStack = new Stack<>();


    private ActivityManager() {}

    public static ActivityManager getInstance() {
        if (instance == null) {
            synchronized (ActivityManager.class) {
                if (instance == null) instance = new ActivityManager();
            }
        }
        return instance;
    }

    public void attach(Activity activity) {activityStack.add(activity);}
    public void detach(Activity activity) {activityStack.remove(activity);}
    public void finish(Activity finishActivity) {
        int size = activityStack.size();
        for (int i = 0; i < size; i++) {
            if (finishActivity == activityStack.get(i)) {
                activityStack.remove(finishActivity);
                finishActivity.finish();
                i--;
                size--;
            }
        }
    }
    public void finish(Class<? extends Activity> activityClass) {
        int size = activityStack.size();
        for (int i = 0; i < size; i++) {
            Activity activity = activityStack.get(i);
            if (activity.getClass().getCanonicalName().equals(activityClass.getCanonicalName())) {
                activityStack.remove(activity);
                activity.finish();
                i--;
                size--;
            }
        }
    }
    public void finishAll() {
        Iterator<Activity> activityIterable = activityStack.iterator();
        while (activityIterable.hasNext()) {
            Activity activity = activityIterable.next();
            activityStack.remove(activity);
            activity.finish();
        }
    }
    public Activity currentActivity() {return activityStack.lastElement();}
    public void exitApplication() {
        finishAll();
        System.exit(0);
    }

}
