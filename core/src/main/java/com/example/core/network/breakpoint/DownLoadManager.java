package com.example.core.network.breakpoint;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownLoadManager {
    private static final AtomicReference<DownLoadManager> INSTANCE = new AtomicReference<>();
    private Map<String, Call> downloadCalls;
    private OkHttpClient mClient;

    public static DownLoadManager getInstance() {
        for (; ; ) {
            DownLoadManager instance = INSTANCE.get();
            if (instance != null) return instance;
            instance = new DownLoadManager();
            if (INSTANCE.compareAndSet(null, instance)) return instance;
        }
    }

    private DownLoadManager() {
        downloadCalls = new HashMap<>();
        mClient = new OkHttpClient.Builder().build();
    }

    public void downLoad(String url, DownLoadObserver downLoadObserver) {
        Observable.just(url)
                .filter(s -> !downloadCalls.containsKey(s))
                .flatMap(s -> Observable.just(createDownInfo(s)))
                .map(this::getRealFileName)
                .flatMap(downloadInfo -> Observable.create(new DownloadSubscribe(downloadInfo)))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(downLoadObserver);
    }

    private DownInfo createDownInfo(String url) {
        DownInfo info = new DownInfo(url);
        info.setTotal(getContentLength(url));
        String fileName = url.substring(url.indexOf("/"));
        info.setFileName(fileName);
        return info;
    }

    public DownInfo getRealFileName(DownInfo info) {
        String fileName = info.getFileName();
        long downloadLength = 0, contentLength = info.getTotal();
        File file = new File(fileName);
        if (file.exists()) {
            downloadLength = file.length();
        }
        int i = 0;
        while (downloadLength >= contentLength) {
            int dotIndex = fileName.lastIndexOf(".");
            String fileNameOther;
            if (dotIndex == -1) {
                fileNameOther = fileName + "(" + i + ")";
            } else {
                fileNameOther = fileName.substring(0, dotIndex)
                        + "(" + i + ")" + fileName.substring(dotIndex);
            }
            File newFile = new File(fileNameOther);
            file = newFile;
            downloadLength = newFile.length();
        }
        info.setProgress(downloadLength);
        info.setFileName(file.getName());
        return info;
    }

    public void cancel(String url) {
        Call call = downloadCalls.get(url);
        if (call != null) call.cancel();
        downloadCalls.remove(url);
    }

    public long getContentLength(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = mClient.newCall(request).execute();
            if (response != null && response.isSuccessful()) {
                long contentLength = response.body().contentLength();
                response.close();
                return contentLength == 0 ? DownInfo.TOTAL_ERROR : contentLength;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return DownInfo.TOTAL_ERROR;
    }

    private class DownloadSubscribe implements ObservableOnSubscribe<DownInfo> {

        private DownInfo downInfo;

        public DownloadSubscribe(DownInfo downInfo) {
            this.downInfo = downInfo;
        }

        @Override
        public void subscribe(@NonNull ObservableEmitter<DownInfo> e) throws Throwable {
            String url = downInfo.getUrl();
            long downloadLength = downInfo.getProgress();
            long contentLength = downInfo.getTotal();
            e.onNext(downInfo);
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("RANGE", "bytes=" + downloadLength + "-" + contentLength)
                    .build();
            Call call = mClient.newCall(request);
            downloadCalls.put(url, call);
            Response response = call.execute();
            File file = new File(downInfo.getFileName());
            InputStream is = null;
            FileOutputStream fos = null;
            try {
                is = response.body().byteStream();
                fos = new FileOutputStream(file);
                int len;
                byte[] bytes = new byte[2048];
                while ((len = is.read(bytes)) != -1) {
                    fos.write(bytes, 0, len);
                    downloadLength += len;
                    downInfo.setProgress(downloadLength);
                    e.onNext(downInfo);
                }
                fos.flush();
                downloadCalls.remove(url);
            } finally {
                fos.close();
                is.close();
            }
            e.onComplete();
        }
    }

}
