package com.example.core.network.net.exception;

import okhttp3.Response;

/**
 * desc   : 服务器响应异常
 */
public final class ResponseException extends HttpException {

    private final Response mResponse;

    public ResponseException(String message, Response response) {
        super(message);
        mResponse = response;
    }

    public ResponseException(String message, Throwable cause, Response response) {
        super(message, cause);
        mResponse = response;
    }

    public Response getResponse() {
        return mResponse;
    }
}