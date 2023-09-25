package com.example.core.network.net;

import androidx.lifecycle.LifecycleOwner;

import com.example.core.network.net.request.GetRequest;
import com.example.core.network.net.request.PostRequest;

public class MyHttp {

    public static GetRequest getRequest(LifecycleOwner lifecycleOwner) {return new GetRequest(lifecycleOwner);}

    public static PostRequest postRequest(LifecycleOwner lifecycleOwner) {return new PostRequest(lifecycleOwner);}


}
