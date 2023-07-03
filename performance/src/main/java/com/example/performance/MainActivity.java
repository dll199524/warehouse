package com.example.performance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Debug;
import android.util.Log;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
//        Debug.stopMethodTracing();
        Log.d("TAG", "onWindowFocusChanged: " + MainActivity.class);
    }

    @Override
    protected void onPause() {
        super.onPause();

    }


}