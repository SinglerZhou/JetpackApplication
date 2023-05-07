package com.zxg.jetpack.application.mvvmtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.DataBinderMapperImpl;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.zxg.jetpack.application.R;
import com.zxg.jetpack.application.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private ActivityLoginBinding mDataBinding;
    private LoginViewModel mLoginViewModel;
    private RequestLoginViewModel mRequestLoginViewModel;
    private boolean mValue;
    private AlertDialog mAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);

        // dataBinding 替换了 setContentView
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        // view model
        mLoginViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(LoginViewModel.class);

        mRequestLoginViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(RequestLoginViewModel.class);

        mDataBinding.setLoginVm(mLoginViewModel);

        initProgressDialog();

        mDataBinding.setClick(new OnClick() {
            @Override
            public void onLogin() {
                Log.i(TAG, "onLogin");
                // 请求数据
                // 直接从ui拿账号密码
//                String username = mDataBinding.loginUserNameEdit.getText().toString();
//                String password = mDataBinding.loginPasswordEdit.getText().toString();

                // 从viewModel去拿账号密码
                String password = mLoginViewModel.password.getValue();
                String username = mLoginViewModel.userName.getValue();

                Log.i(TAG, "username = " + username);
                Log.i(TAG, "password = " + password);
                if (username != null && password != null) {
                    mRequestLoginViewModel.login(username, password);
                    mAlertDialog.show();
                } else {
                    Log.e(TAG, "username or password is null");
                }
            }

            @Override
            public void isShowPassword() {
                Log.i(TAG, "isShowPassword");
                LiveData<Boolean> isShowPassword = mLoginViewModel.isShowPassword;
                if (isShowPassword != null) {
                    mValue = isShowPassword.getValue() == null || isShowPassword.getValue();
                    mLoginViewModel.isShowPassword.postValue(!mValue);
                    Log.i(TAG, "isShowPassword = " + mValue);
                } else {
                    Log.e(TAG, "isShowPassword is null");
                }
            }
        });

        initObserver();

    }

    private void initProgressDialog() {
        mAlertDialog = new ProgressDialog.Builder(this)
                .setMessage("Loading...").create();
    }

    private void initObserver() {
        mLoginViewModel.isShowPassword.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Log.i(TAG, "isShowPassword = " + aBoolean);
            }
        });

        mLoginViewModel.loginState.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.i(TAG, "loginState = " + s);
            }
        });

        mRequestLoginViewModel.mLoginSuccessData.observe(this, new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse loginResponse) {
                Log.i(TAG, "mLoginSuccessData = " + loginResponse);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAlertDialog.dismiss();
                        mDataBinding.loginState.setText(loginResponse.getMessage());
                    }
                });
            }
        });

        mRequestLoginViewModel.mLoginFailData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.i(TAG, "mLoginFailData = " + s);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAlertDialog.dismiss();
                        mDataBinding.loginState.setText(s);
                    }
                });
            }
        });
    }

}