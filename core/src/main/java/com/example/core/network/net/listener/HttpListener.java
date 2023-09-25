package com.example.core.network.net.listener;

import okhttp3.Call;

public interface HttpListener<T> {

    default void onStart(Call call) {}
    default void onSuccess(T result, boolean cache) {onSuccess(result);}

    void onSuccess(T result);

}
