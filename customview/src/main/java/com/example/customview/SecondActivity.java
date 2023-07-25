package com.example.customview;


import android.content.Intent;
import android.os.Bundle;


import com.example.customview.databinding.ActivitySecondBinding;


public class SecondActivity extends BaseActivity {

    private ActivitySecondBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.button1.setOnClickListener(v -> {
            startActivity(new Intent(this, ThirdActivity.class));
        });

    }
}