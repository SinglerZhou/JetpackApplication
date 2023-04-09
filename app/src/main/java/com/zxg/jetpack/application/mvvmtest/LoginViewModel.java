package com.zxg.jetpack.application.mvvmtest;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {
    public MutableLiveData<String> userName = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    public MutableLiveData<String> loginState = new MutableLiveData<>();
    public MutableLiveData<Boolean> isShowPassword = new MutableLiveData<>();
}
