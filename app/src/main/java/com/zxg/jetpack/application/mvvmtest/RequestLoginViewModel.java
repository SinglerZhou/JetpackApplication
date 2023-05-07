package com.zxg.jetpack.application.mvvmtest;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RequestLoginViewModel extends ViewModel {

    private static final String TAG = "RequestLoginViewModel";
    public MutableLiveData<LoginResponse> mLoginSuccessData = new MutableLiveData<>();

    // 登录失败的状态
    public MutableLiveData<String> mLoginFailData = new MutableLiveData<>();

    public void login(String username, String password) {
        Log.d(TAG, "login");
        HttpRequestManager.getInstance().login(username, password, mLoginSuccessData, mLoginFailData);
    }
}
