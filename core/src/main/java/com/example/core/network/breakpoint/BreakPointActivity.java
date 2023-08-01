package com.example.core.network.breakpoint;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.core.R;
import com.example.core.databinding.ActivityBreakPointBinding;

public class BreakPointActivity extends AppCompatActivity {

    ActivityBreakPointBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBreakPointBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}