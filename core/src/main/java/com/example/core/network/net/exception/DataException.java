package com.example.core.network.net.exception;

/**
 * desc   : 数据解析异常
 */
public final class DataException extends HttpException {

    public DataException(String message) {
        super(message);
    }

    public DataException(String message, Throwable cause) {
        super(message, cause);
    }
}