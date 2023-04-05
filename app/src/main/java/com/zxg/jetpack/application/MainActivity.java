package com.zxg.jetpack.application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.textclassifier.TextLinks;

import com.google.gson.Gson;
import com.zxg.jetpack.application.databinding.ActivityMainBinding;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private ActivityMainBinding mDataBinding;
    private WordDataBase mDataBase;
    private WordDao mWordDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

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
        initRoom();
        initEvent();
    }

    private void initRoom() {
        mDataBase = Room.databaseBuilder(this, WordDataBase.class, "room_data_base_word").build();
        mWordDao = mDataBase.getWordDao();
    }

    private void initEvent() {
        mDataBinding.roomInsert.setOnClickListener(view -> {
            Word word = new Word("hello", "你好");
            Word word2 = new Word("you", "你");
            Word word3 = new Word("me", "我");
            Word word4 = new Word("world", "世界");
            mWordDao.insertWords(word, word2, word3, word4);
            Log.i(TAG, "insert");

            updateView();
        });
        mDataBinding.roomDelete.setOnClickListener(view -> {
            Word word3 = new Word("me", "我");
            word3.setUid(2); // 删除id为 2的word条目
            mWordDao.deleteWords(word3);
            Log.i(TAG, "delete");
        });
        mDataBinding.roomQuery.setOnClickListener(view -> {

            Log.i(TAG, "query");
        });
        mDataBinding.roomUpdate.setOnClickListener(view -> {
            Log.i(TAG, "update");
        });

        // 测试okhttp请求
        mDataBinding.okhttpGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doGet();
            }
        });
    }

    private void updateView() {
        StringBuilder roomData = new StringBuilder();
        List<Word> allWords = mWordDao.getAllWords();
        for (int i = 0; i < allWords.size(); i++) {
            Word word = allWords.get(i);
            roomData.append(word.uid).append(":").append(word.getFirstName()).append(" = ").append(word.getLastName()).append("\n");
        }

        mDataBinding.roomText.setText(roomData.toString());
    }



    private void initData() {
//        User user = new User("张三", 28, 172, "女");
//        mDataBinding.setUser(user);
    }


    public void doGet() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "start do get request");
                OkHttpClient okhttpClient = new OkHttpClient();
                Request request = new Request.Builder()
//                        .url("https://www.wanandroid.com/blog/show/2")
                        .url("https://www.wanandroid.com/article/list/0/json")
                        .build();
                Call call = okhttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Log.i(TAG, "onFailure e = " + e);
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        String string = response.body().string();
                        Log.i(TAG, "onResponse response:" + string);
                        Gson gson = new Gson();
                        WanAndroidBean wanAndroidBean = gson.fromJson(string, WanAndroidBean.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String link = wanAndroidBean.getData().getDatas().get(0).getLink();
                                mDataBinding.okhttpResult.setText("size: " + wanAndroidBean.getData().getSize()+ "\n " + link);
                            }
                        });
                    }
                });
            }
        }).start();
    }
}