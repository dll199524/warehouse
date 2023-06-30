package com.example.car.Hvac;


import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.fwk.sdk.hvac.HvacManager;

public class AppInjection {

    private final static AppViewModelFactory mViewModelFactory = new AppViewModelFactory();

    public static <T extends ViewModel> T getViewModel(ViewModelStoreOwner store, Class<T> clazz) {
        return new ViewModelProvider(store, mViewModelFactory).get(clazz);
    }
    public static AppViewModelFactory getViewModelFactory() {return mViewModelFactory;}
    public static HvacManager getHvacManager() {return HvacManager.getInstance();}
    public static HvacRepository getHvacRepository() {return new HvacRepository(getHvacManager());}

}
