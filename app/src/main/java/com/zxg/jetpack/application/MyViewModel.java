package com.zxg.jetpack.application;

import android.os.Handler;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {
    private static final String TAG = MyViewModel.class.getSimpleName();
    private final MutableLiveData<User> mLiveData = new MutableLiveData<User>();

    public LiveData<User> getLiveData() {
        return mLiveData;
    }

    public void startGetValue() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "run after 5000ms");
                User user = new User("李四", 18, 170, "女");

                mLiveData.postValue(user);
            }
        }, 5000);

    }
}
