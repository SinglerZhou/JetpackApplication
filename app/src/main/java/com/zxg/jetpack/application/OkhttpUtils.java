package com.zxg.jetpack.application;

import okhttp3.OkHttpClient;

public class OkhttpUtils {

    private static OkhttpUtils mInstance;
    private final OkHttpClient mOkhttpClient;

    private OkhttpUtils() {
        mOkhttpClient = new OkHttpClient();
    }

    public static OkhttpUtils getInstance() {
        if (mInstance == null) {
            synchronized (OkhttpUtils.class) {
                if (mInstance == null) {
                    mInstance = new OkhttpUtils();
                }
            }
        }
        return mInstance;
    }

    public OkHttpClient getOkhttpClient() {
        return mOkhttpClient;
    }
}
