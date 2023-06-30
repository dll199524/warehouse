package com.example.car.mvvm.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.car.mvvm.viewmodel.BaseViewModel;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseMvvmFragment <V extends ViewDataBinding, Vm extends ViewModel> extends BaseBindingFragment {

    protected Vm mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initViewModel();
        View view =  super.onCreateView(inflater, container, savedInstanceState);
        initObservable(mViewModel);
        if (getViewModelVariable() != 0) {
            binding.setVariable(getViewModelVariable(), mViewModel);
        }
        return view;
    }

    private void initViewModel() {
        Class<Vm> modelClass;
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType)
            modelClass = (Class<Vm>) ((ParameterizedType) type).getActualTypeArguments()[1];
        else modelClass = (Class<Vm>) BaseViewModel.class;
        Object  object = getViewModelOrFactory();
        if (object instanceof ViewModel){
            mViewModel = (Vm) object;
        }else if (object instanceof ViewModelProvider.Factory){
            mViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) object)
                    .get(modelClass);
        }else {
            mViewModel = new ViewModelProvider(this,
                    new ViewModelProvider.NewInstanceFactory()).get(modelClass);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        loadData(mViewModel);
    }

    public Vm getViewModel() {return mViewModel;}

    public abstract int getViewModelVariable();
    public abstract void loadData(Vm viewModel);
    public abstract void initObservable(Vm viewModel);
    public abstract Object getViewModelOrFactory();


}
