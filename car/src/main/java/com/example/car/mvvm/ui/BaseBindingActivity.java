package com.example.car.mvvm.ui;


import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;


public abstract class BaseBindingActivity<V extends ViewDataBinding> extends BaseActivity {

    protected V binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutId() == 0)
            throw new RuntimeException("layout id must not null");
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        binding.setLifecycleOwner(this);
        binding.executePendingBindings();
        initView();
    }

    public V getBinding() {return binding;}

    @LayoutRes
    protected abstract int getLayoutId();
    protected abstract void initView();
}
