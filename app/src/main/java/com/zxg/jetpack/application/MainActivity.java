package com.zxg.jetpack.application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.zxg.jetpack.application.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private ActivityMainBinding mDataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initData();

        MyViewModel myViewModel
                = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory())
                .get(MyViewModel.class);
        myViewModel.startGetValue();

        // addObserver要在下面的liveData.observe之前
        getLifecycle().addObserver(new MyObserver(myViewModel));

        myViewModel.getLiveData().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                Log.i(TAG, "user:" + user);
                mDataBinding.setUser(user);
            }
        });
    }

    private void initData() {
//        User user = new User("张三", 28, 172, "女");
//        mDataBinding.setUser(user);
    }
}