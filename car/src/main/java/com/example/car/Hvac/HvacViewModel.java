package com.example.car.Hvac;

import android.view.View;

import androidx.lifecycle.MutableLiveData;

import com.example.car.mvvm.utils.AppExecutors;
import com.example.car.mvvm.viewmodel.BaseViewModel;


public class HvacViewModel extends BaseViewModel<HvacRepository> {

    private final HvacRepository hvacRepository;
    private final AppExecutors mAppExecutors;
    private MutableLiveData<String> mTempLive;

    private HvacCallback callback = new HvacCallback() {
        @Override
        public void onTemperatureChange(String temp) {
            getTempLive().postValue(temp);
        }
    };

    public HvacViewModel(HvacRepository repository, AppExecutors mAppExecutors) {
        super(repository);
        this.hvacRepository = repository;
        this.mAppExecutors = mAppExecutors;
        hvacRepository.setHvacListener(callback);
    }

    public void requestTemperature() {hvacRepository.requestTemperature();}
    public void setTemperature(View view) {repository.setTemperature(getTempLive().getValue());}

    @Override
    protected void onCleared() {
        super.onCleared();
        hvacRepository.removeHvacListener();
        hvacRepository.release();
    }

    public MutableLiveData<String> getTempLive() {
        if (mTempLive == null) {
            mTempLive = new MutableLiveData<>();
        }
        return mTempLive;
    }
}
