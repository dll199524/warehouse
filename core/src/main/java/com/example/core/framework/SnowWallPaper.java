package com.example.core.framework;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.service.wallpaper.WallpaperService;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import androidx.annotation.NonNull;

import com.example.core.R;
import com.example.core.utils.SnowUtils;

public class SnowWallPaper extends WallpaperService {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public Engine onCreateEngine() {
        return new ShowEngine();
    }

    public class ShowEngine extends Engine {

        private SnowUtils mSnowUtils;
        private Handler mHandler = new MyHandler();
        Bitmap mBackground;

        private static final int MSG_DRAW_FRAME = 1;
        private static final int MSG_PRODUCE_SHOW = 2;

        public ShowEngine() {}
        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            setTouchEventsEnabled(true);
        }

        @Override
        public void onTouchEvent(MotionEvent event) {
            super.onTouchEvent(event);
            if (event.getAction() == MotionEvent.ACTION_DOWN)
                mSnowUtils.produceInstantFlake((int) event.getX(), (int) event.getY());
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);
            if (visible) {
                mHandler.obtainMessage(MSG_DRAW_FRAME).sendToTarget();
                mHandler.obtainMessage(MSG_PRODUCE_SHOW).sendToTarget();
            } else {mHandler.removeCallbacksAndMessages(null);}
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
            drawFrame();
        }

        @Override
        public void onOffsetsChanged(float xOffset, float yOffset, float xOffsetStep, float yOffsetStep, int xPixelOffset, int yPixelOffset) {
            super.onOffsetsChanged(xOffset, yOffset, xOffsetStep, yOffsetStep, xPixelOffset, yPixelOffset);
            drawFrame();
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            mHandler.removeCallbacksAndMessages(null);
        }

        private void drawFrame() {
            SurfaceHolder sh = getSurfaceHolder();
            final Rect surfaceFrame = sh.getSurfaceFrame();
            final int dw = surfaceFrame.width();
            final int dh = surfaceFrame.height();
            if (mSnowUtils == null) {
                mSnowUtils = new SnowUtils(getApplicationContext());
                mSnowUtils.init(dw, dh);
            }
            if (mBackground == null) {
                mBackground = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background);
            }
            Canvas canvas = sh.lockCanvas();
            if (canvas != null) {
                try {
                    drawBackground(canvas, dw, dh);
                    mSnowUtils.draw(canvas);
                } finally {sh.unlockCanvasAndPost(canvas);}
            }
        }

        private void drawBackground(Canvas canvas, int dw, int dh) {
            if (mBackground != null) {
                RectF dest = new RectF(0, 0, dw, dh);
                canvas.drawBitmap(mBackground, null, dest, null);
            }
        }

        public class MyHandler extends Handler {

            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case MSG_DRAW_FRAME:
                        drawFrame();
                        removeMessages(MSG_DRAW_FRAME);
                        sendMessageDelayed(obtainMessage(MSG_DRAW_FRAME), 20);
                        break;
                    case  MSG_PRODUCE_SHOW:
                        mSnowUtils.produceSnowFlake();
                        removeMessages(MSG_PRODUCE_SHOW);
                        sendMessageDelayed(obtainMessage(MSG_PRODUCE_SHOW), 1000);
                        break;
                    default:
                        break;
                }
            }
        }

    }
}
