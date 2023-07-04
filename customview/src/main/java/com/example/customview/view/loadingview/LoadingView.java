package com.example.customview.view.loadingview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.customview.R;
import com.example.customview.utils.DensityUtil;

public class LoadingView extends LinearLayout {

    private ShapeView mShapeView;
    private View shadowView;
    private boolean isStopAnimator = false;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(getContext(), R.layout.loading_view, this);
        mShapeView = findViewById(R.id.view_shape);
        shadowView = findViewById(R.id.view_shadow);
        post(() -> {startFallAnimation();});
    }

    private void startFallAnimation() {
        if (isStopAnimator) return;
        ObjectAnimator fallAnimation = ObjectAnimator.ofFloat(mShapeView, "translationY",
                0, DensityUtil.dip2px(getContext(), 80));
        ObjectAnimator scaleAnimation = ObjectAnimator.ofFloat(shadowView, "scaleX",
                1.0f, 0.3f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(fallAnimation, scaleAnimation);
        animatorSet.setDuration(400);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                startUpAnimation();
                mShapeView.exchange();
            }
        });
        animatorSet.start();
    }

    private void startUpAnimation() {
        ObjectAnimator fallAnimation = ObjectAnimator.ofFloat(mShapeView, "translationY",
                DensityUtil.dip2px(getContext(), 80), 0);
        ObjectAnimator scaleAnimation = ObjectAnimator.ofFloat(shadowView, "scaleX",
                0.3f, 1.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(fallAnimation, scaleAnimation);
        animatorSet.setDuration(400);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                startFallAnimation();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                startRotateAnimation();
            }
        });
        animatorSet.start();
    }

    private void startRotateAnimation() {
        if (isStopAnimator) return;
        ObjectAnimator rotateAnimation = null;
        switch (mShapeView.getCurrentShape()) {
            case Circle:
            case Square:
                rotateAnimation = ObjectAnimator.ofFloat(mShapeView, "rotation",
                        0, 180);
                break;
            case Triangle:
                rotateAnimation = ObjectAnimator.ofFloat(mShapeView, "rotation",
                        0, -120);
                break;
        }
        rotateAnimation.setDuration(400);
        rotateAnimation.setInterpolator(new DecelerateInterpolator());
        rotateAnimation.start();
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(INVISIBLE);
        mShapeView.clearAnimation();
        shadowView.clearAnimation();
        ViewGroup parent = (ViewGroup) getParent();
        if (parent != null) {
            parent.removeView(this);
            parent.removeAllViews();
        }
        mShapeView = null;
        shadowView = null;
    }
}
