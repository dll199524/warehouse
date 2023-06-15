package com.example.performance.startup.provider;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.text.TextUtils;

import com.example.performance.startup.StartUp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StartupInitializer {
    public static String META_VALUE = "android.startup";

    public static List<StartUp<?>> discoverAndInitializ(Context context,
                                                        String providerName) throws Exception {
        Map<Class<? extends StartUp>, StartUp<?>> startups = new HashMap<>();
        //获得manifest contentProvider中的meta-data
        ComponentName provider = new ComponentName(context, providerName);
        ProviderInfo providerInfo = context.getPackageManager().getProviderInfo(provider,
                PackageManager.GET_META_DATA);
        //得到manifest中配置的任务
        for (String key : providerInfo.metaData.keySet()) {
            String value = providerInfo.metaData.getString(key);
            if (TextUtils.equals(META_VALUE, value)) {
                Class<?> clazz = Class.forName(key);
                if (StartUp.class.isAssignableFrom(clazz)) {
                    doInitialize((StartUp<?>) clazz.newInstance(), startups);
                }
            }
        }

        List<StartUp<?>> result = new ArrayList<>(startups.values());
        return result;
    }

    private static void doInitialize(StartUp<?> startup,
                                     Map<Class<? extends StartUp>, StartUp<?>> startups) throws Exception {
        //避免重复 不能使用List
        startups.put(startup.getClass(), startup);
        if (startup.getDependcies() != 0) {
            //遍历父任务
            for (Class<? extends StartUp<?>> dependency : startup.dependencies()) {
                doInitialize(dependency.newInstance(), startups);
            }
        }
    }


}
