package com.example.common.mod.build;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.common.R;
import java.util.Map;

public class AbsNavigationBar implements INavigation {

    protected Builder mBuilder;
    private View mNavigationBar;
    protected AbsNavigationBar(Builder builder) {
        mBuilder = builder;
        View navigationBar = createNavigationBar(mBuilder.mContext, R.layout.view_navigation_bar,
                mBuilder.mParent);
        attachParent(navigationBar, mBuilder.mParent);
        addParams();
    }

    @Override
    public View createNavigationBar(Context context, int layoutId, ViewGroup parent) {
        mNavigationBar = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return mNavigationBar;
    }

    @Override
    public void attachParent(View view, ViewGroup parent) {
        parent.addView(view);
    }

    @Override
    public void addParams() {
        for (Map.Entry<Integer, String> entry : mBuilder.mTextMap.entrySet()) {
            View view = findViewById(entry.getKey());
            if (view instanceof TextView) ((TextView) view).setText(entry.getValue());
        }
        for (Map.Entry<Integer, View.OnClickListener> entry : mBuilder.mListenerMap.entrySet()) {
            View view = findViewById(entry.getKey());
            view.setOnClickListener(entry.getValue());
        }

    }

    public <T extends View> View findViewById(int id) {
        return mNavigationBar.findViewById(id);
    }

    public static abstract class Builder {
        protected Context mContext;
        protected int mLayout;
        protected ViewGroup mParent;
        Map<Integer, String> mTextMap;
        Map<Integer, View.OnClickListener> mListenerMap;

        public Builder(Context context, int layout, ViewGroup parent) {
            mContext = context;
            mLayout = layout;
            mParent = parent;
        }

        public abstract AbsNavigationBar build();
        public Builder setText(int viewId, String text) {
            mTextMap.put(viewId, text);
            return this;
        }
        public Builder setClickListener(int viewId, View.OnClickListener listener) {
            mListenerMap.put(viewId, listener);
            return this;
        }
    }
}
