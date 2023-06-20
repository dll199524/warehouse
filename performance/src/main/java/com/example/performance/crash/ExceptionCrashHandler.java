package com.example.performance.crash;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class ExceptionCrashHandler implements Thread.UncaughtExceptionHandler {

    private static final String TAG = "ExceptionCrashHandler";
    private volatile static ExceptionCrashHandler instance;
    private Thread.UncaughtExceptionHandler exceptionHandler;
    private WeakReference<Context> context;

    public void init(Context context) {
        Thread.currentThread().setUncaughtExceptionHandler(this);
        this.exceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        this.context = new WeakReference<>(context);
    }


    @Override
    public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
        Log.d(TAG, "uncaughtException: " + e.getMessage());
        String crashFileName = saveCacheInfo(e);
        Log.d(TAG, "fileName: " + crashFileName);
        exceptionHandler.uncaughtException(t, e);
    }

    private String saveCacheInfo(Throwable e) {
        String fileName = null;
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : obtainSimpleInfo(context.get()).entrySet()) {
            sb.append(entry.getKey());
            sb.append(entry.getValue());
        }
        sb.append(obtainExceptionInfo(e));
        File dir = new File(context.get().getFilesDir() + File.separator +
                "crash" + File.separator);
        if (dir.exists()) deleteDir(dir);
        if (!dir.exists()) {
            boolean res = dir.mkdirs();
            if (!res) Log.d(TAG, "saveCacheInfo: create file failed");
        }
        try {
            fileName = dir.toString() + File.separator +
                    getAssignTime() + ".text";
            FileOutputStream fos = new FileOutputStream(fileName);
            fos.write(sb.toString().getBytes());
            fos.flush();
            fos.close();
        } catch (IOException exception) {exception.printStackTrace();}
        return fileName;
    }


    private HashMap<String, String> obtainSimpleInfo(Context context) {
        HashMap<String, String> map = new HashMap<>();
        PackageManager pkm = context.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = pkm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        map.put("version", packageInfo.versionName);
        map.put("versionCode", "" + packageInfo.versionCode);
        map.put("MODEL", Build.MODEL);
        map.put("SDK_INT", packageInfo.versionName);
        map.put("PRODUCT", Build.PRODUCT);
        map.put("MODEL_INFO", getMoblieInfo());
        return map;
    }

    private String getMoblieInfo() {
        StringBuilder sb = new StringBuilder();
        try {
            Field[] fields = Build.class.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                String name = field.getName();
                String val = field.get(null).toString();
                sb.append(name).append("=").append(val).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


    private String obtainExceptionInfo(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        pw.close();
        return sw.toString();
    }

    public void deleteDir(File file) {
        boolean res;
        if (file.isFile()) {
            res = file.delete();
            if (!res) Log.d(TAG, "deleteDir: delete file failed");
        }
        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                res = file.delete();
                if (!res) Log.d(TAG, "deleteDir: delete file failed");
                return;
            }
            for (File childFile : childFiles) deleteDir(childFile);
            res = file.delete();
            if (!res) Log.d(TAG, "deleteDir: delete file failed");
        }
    }

    private String getAssignTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm");
        long currentTime = System.currentTimeMillis();
        return dateFormat.format(currentTime);
    }


    private ExceptionCrashHandler() {
    }

    public static ExceptionCrashHandler getInstance() {
        if (instance == null) {
            synchronized (ExceptionCrashHandler.class) {
                if (instance == null) instance = new ExceptionCrashHandler();
            }
        }
        return instance;
    }
}

//native crash google breakpad signal
