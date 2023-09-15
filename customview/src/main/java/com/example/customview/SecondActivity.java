package com.example.customview;


import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;


import com.example.customview.databinding.ActivitySecondBinding;

import java.io.File;


public class SecondActivity extends BaseActivity {

    private ActivitySecondBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        binding.button1.setOnClickListener(v -> {
//            startActivity(new Intent(this, ThirdActivity.class));
//        });
        File file = Environment.getExternalStoragePublicDirectory("Android");
        if (file.exists()) {
            File[] files = file.listFiles();
            for (File f : files) {
                Log.d("TAG", "onCreate: " + f.getName());
            }
        }

    }
}