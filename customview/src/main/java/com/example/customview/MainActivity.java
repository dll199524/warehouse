package com.example.customview;


import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;


import com.example.customview.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 2000);
        valueAnimator.addUpdateListener(animation -> {
            float current = (float) animation.getAnimatedValue();
            binding.stepView.setCurrentStep(current);
        });
        valueAnimator.setDuration(5000);
        valueAnimator.start();

        ValueAnimator valueAnimator1 = ValueAnimator.ofFloat(0, 1);
        valueAnimator1.addUpdateListener(animation -> {
            float progress = (float) animation.getAnimatedValue();
            binding.colorTrack.setProgress(progress);
        });
        valueAnimator1.setDuration(2000);
        valueAnimator1.start();

        binding.button.setOnClickListener(v -> {
            Intent in = new Intent();
            in.setClass(this, SecondActivity.class);
            startActivity(in);
        });
    }


}