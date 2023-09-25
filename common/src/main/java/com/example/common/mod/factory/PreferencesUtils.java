package com.example.common.mod.factory;

import android.content.Context;
import android.content.SharedPreferences;


public class PreferencesUtils {

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    private PreferencesUtils() {}
    private static class Holder {
        private static PreferencesUtils instance = new PreferencesUtils();
    }
    public static PreferencesUtils getInstance() {return Holder.instance;}

    public void init(Context context) {
        mSharedPreferences = context.getApplicationContext().getSharedPreferences("cache", Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public PreferencesUtils putString(String key, String val) {
        mEditor.putString(key, val);
        return this;
    }

    public PreferencesUtils putInt(String key, int val) {
        mEditor.putInt(key, val);
        return this;
    }

    public void commit() {mEditor.commit();}

    public String getString(String key, String defVal) {
        return mSharedPreferences.getString(key, defVal);
    }

    public int getInt(String key, int defVal) {
        return mSharedPreferences.getInt(key, defVal);
    }


}
