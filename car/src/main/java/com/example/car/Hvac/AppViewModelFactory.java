package com.example.car.Hvac;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.car.mvvm.utils.AppExecutors;

import java.lang.reflect.InvocationTargetException;


public class AppViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        try {
            if (modelClass == HvacViewModel.class)
                return modelClass.getConstructor(HvacRepository.class, AppExecutors.class).
                        newInstance(AppInjection.getHvacRepository(), AppExecutors.get());
            else
                throw new RuntimeException("modelClass create failed");

        } catch (NoSuchMethodException | InvocationTargetException |
                IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
