package com.example.car.Hvac;

import android.text.TextUtils;

import com.example.car.mvvm.model.BaseRepository;
import com.fwk.sdk.hvac.HvacManager;
import com.fwk.sdk.hvac.IHvacCallback;

public class HvacRepository extends BaseRepository {

    private final HvacManager mHvacManager;
    private HvacCallback mHvacCallback;

    private final IHvacCallback iHvacCallback = v -> {
        if (mHvacCallback != null) {
            String temp = String.valueOf(v);
            mHvacCallback.onTemperatureChange(temp);
        }
    };

    public HvacRepository(HvacManager mHvacManager) {
        this.mHvacManager = mHvacManager;
        mHvacManager.registerCallback(iHvacCallback);
    }

    public void requestTemperature() {
        mHvacManager.requestTemperature();
    }

    public void setTemperature(String temp) {
        if (temp == null || TextUtils.isEmpty(temp)) return;
        mHvacManager.setTemperature(Integer.parseInt(temp));
    }

    public void setHvacListener(HvacCallback callback) {mHvacCallback = callback;}
    public void removeHvacListener() {mHvacCallback = null;}
    public void release() {mHvacManager.unregisterCallback(iHvacCallback);}


}
