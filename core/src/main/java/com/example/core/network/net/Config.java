package com.example.core.network.net;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import okhttp3.OkHttpClient;


public final class Config {

    private static volatile Config sConfig;

    public static Config getInstance() {
        if (sConfig == null) {
            // 当前没有初始化配置
            throw new IllegalStateException("You haven't initialized the configuration yet");
        }
        return sConfig;
    }

    private static void setInstance(Config config) {
        sConfig = config;
    }

    public static Config with(OkHttpClient client) {
        return new Config(client);
    }

    /** 服务器配置 */
    private RequestServer mServer;
    /** 请求处理器 */
    private RequestHandler mHandler;
    /** 请求拦截器 */
    private RequestInterceptor mInterceptor;
    /** 日志打印策略 */
    private RequestLogStrategy mLogStrategy;

    /** OkHttp 客户端 */
    private OkHttpClient mClient;

    /** 通用参数 */
    private HashMap<String, Object> mParams;
    /** 通用请求头 */
    private HashMap<String, String> mHeaders;

    /** 线程调度器 */
    private ThreadSchedulers mThreadSchedulers = ThreadSchedulers.MainThread;

    /** 日志开关 */
    private boolean mLogEnabled = true;
    /** 日志 TAG */
    private String mLogTag = "EasyHttp";

    /** 重试次数 */
    private int mRetryCount;
    /** 重试时间 */
    private long mRetryTime = 2000;

    private Config(OkHttpClient client) {
        mClient = client;
        mParams = new HashMap<>();
        mHeaders = new HashMap<>();
    }

    public Config setServer(String host) {
        return setServer(new EasyRequestServer(host));
    }

    public Config setServer(IRequestServer server) {
        mServer = server;
        return this;
    }

    public Config setHandler(IRequestHandler handler) {
        mHandler = handler;
        return this;
    }

    public Config setInterceptor(IRequestInterceptor interceptor) {
        mInterceptor = interceptor;
        return this;
    }

    public Config setClient(OkHttpClient client) {
        mClient = client;
        if (mClient == null) {
            throw new IllegalArgumentException("The OkHttp client object cannot be empty");
        }
        return this;
    }

    public Config setParams(HashMap<String, Object> params) {
        if (params == null) {
            params = new HashMap<>();
        }
        mParams = params;
        return this;
    }

    public Config setHeaders(HashMap<String, String> headers) {
        if (headers == null) {
            headers = new HashMap<>();
        }
        mHeaders = headers;
        return this;
    }

    public Config addHeader(String key, String value) {
        if (key != null && value != null) {
            mHeaders.put(key, value);
        }
        return this;
    }

    public Config removeHeader(String key) {
        if (key != null) {
            mHeaders.remove(key);
        }
        return this;
    }

    public Config addParam(String key, String value) {
        if (key != null && value != null) {
            mParams.put(key, value);
        }
        return this;
    }

    public Config removeParam(String key) {
        if (key != null) {
            mParams.remove(key);
        }
        return this;
    }

    public Config setThreadSchedulers(ThreadSchedulers schedulers) {
        if (mThreadSchedulers == null) {
            // 线程调度器不能为空
            throw new NullPointerException("Thread schedulers cannot be empty");
        }
        mThreadSchedulers = schedulers;
        return this;
    }

    public Config setLogStrategy(IRequestLogStrategy strategy) {
        mLogStrategy = strategy;
        return this;
    }

    public Config setLogEnabled(boolean enabled) {
        mLogEnabled = enabled;
        return this;
    }

    public Config setLogTag(String tag) {
        mLogTag = tag;
        return this;
    }

    public Config setRetryCount(int count) {
        if (count < 0) {
            // 重试次数必须大于等于 0 次
            throw new IllegalArgumentException("The number of retries must be greater than 0");
        }
        mRetryCount = count;
        return this;
    }

    public Config setRetryTime(long time) {
        if (time < 0) {
            // 重试时间必须大于等于 0 毫秒
            throw new IllegalArgumentException("The retry time must be greater than 0");
        }
        mRetryTime = time;
        return this;
    }

    public IRequestServer getServer() {
        return mServer;
    }

    public IRequestHandler getHandler() {
        return mHandler;
    }

    public IRequestInterceptor getInterceptor() {
        return mInterceptor;
    }

    public OkHttpClient getClient() {
        return mClient;
    }

    public HashMap<String, Object> getParams() {
        return mParams;
    }

    public HashMap<String, String> getHeaders() {
        return mHeaders;
    }

    public ThreadSchedulers getThreadSchedulers() {
        return mThreadSchedulers;
    }

    public IRequestLogStrategy getLogStrategy() {
        return mLogStrategy;
    }

    public boolean isLogEnabled() {
        return mLogEnabled && mLogStrategy != null;
    }

    public String getLogTag() {
        return mLogTag;
    }

    public int getRetryCount() {
        return mRetryCount;
    }

    public long getRetryTime() {
        return mRetryTime;
    }

    public void into() {
        if (mClient == null) {
            throw new IllegalArgumentException("Please set up the OkHttpClient object");
        }

        if (mServer == null) {
            throw new IllegalArgumentException("Please set up the RequestServer object");
        }

        if (mHandler == null) {
            throw new IllegalArgumentException("Please set the RequestHandler object");
        }

        try {
            // 校验主机和路径的 url 是否合法
            new URL(mServer.getHost());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("The configured host path url address is not correct");
        }

        if (mLogStrategy == null) {
            mLogStrategy = new EasyHttpLogStrategy();
        }
        Config.setInstance(this);
    }
}