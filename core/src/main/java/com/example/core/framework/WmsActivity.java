package com.example.core.framework;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import android.view.WindowManager;

import com.example.core.R;

public class WmsActivity extends AppCompatActivity {

    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wms);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //高斯模糊
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
                WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            getWindow().getAttributes().setBlurBehindRadius(100);
        }
//        if (getWindow().getRootSurfaceControl() != null &&
//                getWindow().getRootSurfaceControl() instanceof ViewRootImpl) {
//            SurfaceControl surfaceControl = ((ViewRootImpl)getWindow().getRootSurfaceControl()).getSurfaceControl();
//            SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
//            transaction.setBackgroundBlurRadius(surfaceControl,100);
//            transaction.apply();
//            android.util.Log.i("lsm"," setBackgroundBlurRadius");
//        }

    }


}