package com.example.car.mvvm.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.example.car.mvvm.utils.LogUtils;

public abstract class BaseBindingFragment<V extends ViewDataBinding> extends BaseFragment {

    private static final String TAG = "BaseBindingFragment";
    protected V binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtils.logV(TAG, "[onCreateView]");
        if (getLayoutId() == 0)
            throw new RuntimeException("layout id must not be null");
        binding = DataBindingUtil.inflate(getLayoutInflater(), getLayoutId(), container, false);
        binding.setLifecycleOwner(this);
        binding.executePendingBindings();
        return binding.getRoot();
    }

    public V getBinding() {return binding;}

    @LayoutRes
    protected abstract int getLayoutId();
    protected abstract void initView();

}
