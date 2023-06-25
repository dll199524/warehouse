package com.example.car.mvvm.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.car.mvvm.model.BaseRepository;
import com.example.car.mvvm.utils.LogUtils;

public class BaseViewModel <M extends BaseRepository> extends ViewModel {

    private static final String TAG = "BaseViewModel";
    protected M repository;
    public BaseViewModel(M repository) {
        this.repository = repository;
    }

    public M getRepository() {
        return repository;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        LogUtils.logV(TAG, "[onCleared]");
    }
}
