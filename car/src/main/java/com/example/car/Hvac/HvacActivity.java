package com.example.car.Hvac;


import com.example.car.R;
import com.example.car.databinding.ActivityHvacBinding;
import com.example.car.mvvm.ui.BaseMvvmActivity;

//hvac空调交互系统
public class HvacActivity extends BaseMvvmActivity<ActivityHvacBinding, HvacViewModel> {


    @Override
    public int getViewModelVariable() {
        return 0;
    }

    @Override
    public void loadData(HvacViewModel viewModel) {

    }

    @Override
    public void initObservable(HvacViewModel viewModel) {

    }

    @Override
    public Object getViewModelOrFactory() {
        return AppInjection.getViewModelFactory();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hvac;
    }

    @Override
    protected void initView() {

    }
}