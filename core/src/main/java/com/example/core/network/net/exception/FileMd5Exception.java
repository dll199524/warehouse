package com.example.core.network.net.exception;

/**
 * desc   : MD5 校验异常
 */
public final class FileMd5Exception extends HttpException {

    private final String mMd5;

    public FileMd5Exception(String message, String md5) {
        super(message);
        mMd5 = md5;
    }

    public String getMd5() {
        return mMd5;
    }
}