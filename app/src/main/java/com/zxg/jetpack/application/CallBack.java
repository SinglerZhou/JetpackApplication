package com.zxg.jetpack.application;

public interface CallBack {
    void onFail(int errorCode, String errorMsg);
    void onSuccess(String responseJson);
}
