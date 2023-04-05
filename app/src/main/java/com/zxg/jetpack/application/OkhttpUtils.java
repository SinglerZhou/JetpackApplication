package com.zxg.jetpack.application;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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

    public void doGet(String url, CallBack callBack) {
        Request request = new Request.Builder().url(url).build();
        Call call = mOkhttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callBack.onFail(500, e.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                int code = response.code();
                if (code == 200) {
                    String string = response.body().string();
                    callBack.onSuccess(string);
                }
            }
        });
    }
}
