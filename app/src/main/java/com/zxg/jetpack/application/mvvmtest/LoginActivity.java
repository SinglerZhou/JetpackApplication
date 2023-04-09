package com.zxg.jetpack.application.mvvmtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.DataBinderMapperImpl;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import com.zxg.jetpack.application.R;
import com.zxg.jetpack.application.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private ActivityLoginBinding mDataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);

        // dataBinding 替换了 setContentView
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        // view model
        LoginViewModel loginViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(LoginViewModel.class);
        mDataBinding.setClick(new OnClick() {
            @Override
            public void onLogin() {
                Log.i(TAG, "onLogin");

            }

            @Override
            public void isShowPassword() {
                Log.i(TAG, "isShowPassword");
                LiveData<Boolean> isShowPassword = loginViewModel.isShowPassword;
                boolean value = false;
                if (isShowPassword != null) {
                    value = isShowPassword.getValue();
                }
                loginViewModel.isShowPassword.postValue(!value);
            }
        });
        mDataBinding.setLoginVm(loginViewModel);
    }

}