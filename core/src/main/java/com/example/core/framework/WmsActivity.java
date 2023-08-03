package com.example.core.framework;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.ImageFormat;
import android.graphics.PixelFormat;
import android.graphics.SurfaceTexture;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.ImageReader;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceControl;
import android.view.WindowManager;

import com.example.core.R;

public class WmsActivity extends AppCompatActivity {

    private Handler mHandler = new Handler(Looper.getMainLooper());
    private DisplayManager mDisplayManager;
    private static final int WIDTH = 720;
    private static final int HEIGHT = 480;
    private ImageReader mImageReader;
    private Surface mSurface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wms);

        mDisplayManager = (DisplayManager) getSystemService(Context.DISPLAY_SERVICE);
        mImageReader = ImageReader.newInstance(WIDTH, HEIGHT, ImageFormat.RGB_565, 2);
        mSurface = mImageReader.getSurface();
//        while (true) {
//            createVirtualDisplay(mSurface, 100, 100);
//        }
        for (int i = 0; i < 5; i++) {
            createVirtualDisplay(mSurface, 720, 480);
            Log.d("TAG", "surface create" + i);
        }

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


    private VirtualDisplay createVirtualDisplay(Surface surface, int width, int height) {
        return mDisplayManager.createVirtualDisplay("App-VD", width, height, 160, surface,
                DisplayManager.VIRTUAL_DISPLAY_FLAG_OWN_CONTENT_ONLY);
    }

}