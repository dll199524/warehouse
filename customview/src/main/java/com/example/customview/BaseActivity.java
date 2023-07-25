package com.example.customview;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public abstract class BaseActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "[onCreate]");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "[onStart]");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "[onRestart]");
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            Log.d(TAG, "[onNewIntent]" + intent.toString());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "[onActivityResult] requestCode:" + requestCode + ";"
                + "resultCode:" + requestCode);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "[onResume]");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "[onPause]");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "[onStop]");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "[onDestroy]");
    }
}

