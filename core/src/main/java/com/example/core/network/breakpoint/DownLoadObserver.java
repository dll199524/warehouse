package com.example.core.network.breakpoint;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class DownLoadObserver implements Observer<DownInfo> {
    protected Disposable d;
    private DownInfo downInfo;
    @Override
    public void onSubscribe(@NonNull Disposable d) {
        this.d = d;
    }

    @Override
    public void onNext(@NonNull DownInfo downInfo) {
        this.downInfo = downInfo;
    }

    @Override
    public void onError(@NonNull Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
