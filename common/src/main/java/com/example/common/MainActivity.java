package com.example.common;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.example.common.mod.ActivityManager;
import com.example.common.mod.build.DefaultNavigationBar;

public class MainActivity extends AppCompatActivity {

    private ViewGroup mViewGroupRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewGroupRoot = findViewById(R.id.view_root);

        DefaultNavigationBar navigationBar = new DefaultNavigationBar.Builder(this, mViewGroupRoot)
                .setLeftText("返回", v -> finish())
                .setLeftTextVisible(View.VISIBLE)
                .build();

    }

    @Override
    public void finish() {
        super.finish();

    }
}