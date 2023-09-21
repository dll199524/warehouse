package com.example.performance.apm;

import android.app.Application;

public interface ITracker {
    void startTracker(Application application);
    void pauseTracker(Application application);
    void stopTracker(Application application);
}
