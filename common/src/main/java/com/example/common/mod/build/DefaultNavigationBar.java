package com.example.common.mod.build;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.example.common.R;

public class DefaultNavigationBar extends AbsNavigationBar {


    protected DefaultNavigationBar(Builder builder) {
        super(builder);
    }

    @Override
    public void addParams() {
        super.addParams();

        //处理特定参数
        Builder builder = (Builder) mBuilder;
        findViewById(R.id.tv_left).setVisibility(builder.mLeftTextVisible);
    }

    public static class Builder extends AbsNavigationBar.Builder {

        public int mLeftTextVisible = View.VISIBLE;
        public Builder(Context context, ViewGroup parent) {
            super(context, R.layout.view_navigation_bar, parent);
        }
        @Override
        public DefaultNavigationBar build() {
            return new DefaultNavigationBar(this);
        }
        public Builder setLeftText(String text, View.OnClickListener listener) {
            setText(R.id.tv_left, text);
            setClickListener(R.id.tv_left, listener);
            return this;
        }

        public Builder setLeftTextVisible(int visible) {
            mLeftTextVisible = visible;
            return this;
        }
    }
}
