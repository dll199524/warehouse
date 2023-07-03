package com.example.car.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.car.mvvm.model.BaseRepository;
import com.example.car.mvvm.utils.LogUtils;

public class BaseAndroidViewModel <M extends BaseRepository> extends AndroidViewModel {

    private static final String TAG = "BaseAndroidViewModel";
    protected M repository;

    public BaseAndroidViewModel(@NonNull Application application, M repository) {
        super(application);
        this.repository = repository;
    }


    public M getRepository() {return repository;}

    @Override
    protected void onCleared() {
        super.onCleared();
        LogUtils.logV(TAG, "[onCleared]");
    }

}
