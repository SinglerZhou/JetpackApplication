package com.zxg.jetpack.application;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

public class MyObserver implements DefaultLifecycleObserver {
    private static final String TAG = MyObserver.class.getSimpleName();

    @Override
    public void onCreate(LifecycleOwner owner) {
        Log.i(TAG, "onCreate");
    }

    @Override
    public void onStart(LifecycleOwner owner) {
        Log.i(TAG, "onStart");
        Lifecycle.State currentState = owner.getLifecycle().getCurrentState();
        boolean isCreate = currentState.isAtLeast(Lifecycle.State.CREATED);
        Log.i(TAG, "isAtLeast isCreate:" + isCreate);
        boolean isResume = currentState.isAtLeast(Lifecycle.State.RESUMED);
        Log.i(TAG, "isAtLeast isResume:" + isResume);
    }

    @Override
    public void onResume(LifecycleOwner owner) {
        Log.i(TAG, "onResume");

    }

    @Override
    public void onPause(LifecycleOwner owner) {
        Log.i(TAG, "onPause");

    }


    @Override
    public void onStop(LifecycleOwner owner) {
        Log.i(TAG, "onStop");

    }

    @Override
    public void onDestroy(LifecycleOwner owner) {
        Log.i(TAG, "onDestroy");

    }
}
