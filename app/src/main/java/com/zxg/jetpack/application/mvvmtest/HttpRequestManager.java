package com.zxg.jetpack.application.mvvmtest;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

/**
 * 网络请求管理类
 */
public class HttpRequestManager {

    private static final String TAG = HttpRequestManager.class.getSimpleName();

    private static HttpRequestManager instance;

    public static HttpRequestManager getInstance() {
        if (instance == null) {
            synchronized (HttpRequestManager.class) {
                if (instance == null) {
                    instance = new HttpRequestManager();
                }
            }
        }
        return instance;
    }

    private HttpRequestManager() {
    }

    public void login(String username, String password, MutableLiveData<LoginResponse> loginSuccessData, MutableLiveData<String> loginFailData) {
        // 暂时使用线程模拟登录请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (username.equals("admin") && password.equals("123456")) {
                    LoginResponse loginResponse = new LoginResponse();
                    loginResponse.setUsername(username);
                    loginResponse.setPassword(password);
                    loginResponse.setEmail("admin@example.com");
                    loginResponse.setMessage("恭喜你登录成功");
                    loginSuccessData.postValue(loginResponse);
                    Log.d(TAG, "login success");
                } else {
                    loginFailData.postValue("用户名或密码错误");
                    Log.d(TAG, "login fail");
                }
            }
        }).start();
    }
}
