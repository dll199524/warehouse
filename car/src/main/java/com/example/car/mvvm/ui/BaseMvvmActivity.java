package com.example.car.mvvm.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.car.mvvm.viewmodel.BaseViewModel;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseMvvmActivity <V extends ViewDataBinding, Vm extends ViewModel> extends BaseBindingActivity<V> {

    protected Vm mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initViewModel();
        super.onCreate(savedInstanceState);
        if (getViewModelVariable() != 0)
            binding.setVariable(getViewModelVariable(), mViewModel);
        binding.executePendingBindings();
        initObservable(mViewModel);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadData(mViewModel);
    }

    private void initViewModel() {
        Class<Vm> modelClass;
        //getGenericSuperclass()获得带有泛型的父类
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            modelClass = (Class<Vm>) ((ParameterizedType) type).getActualTypeArguments()[1];
        } else {
            modelClass = (Class<Vm>) BaseViewModel.class;
        }
        Object  object = getViewModelOrFactory();
        if (object instanceof BaseViewModel){
            mViewModel = (Vm) object;
        }else if (object instanceof ViewModelProvider.Factory){
            mViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) object)
                    .get(modelClass);
        }else {
            mViewModel = new ViewModelProvider(this,
                    new ViewModelProvider.NewInstanceFactory()).get(modelClass);
        }
    }

    public Vm getViewModel() {return mViewModel;}

    public abstract int getViewModelVariable();
    public abstract void loadData(Vm viewModel);
    public abstract void initObservable(Vm viewModel);
    public abstract Object getViewModelOrFactory();


}
