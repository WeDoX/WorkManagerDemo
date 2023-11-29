package com.example.myapplication;

import android.app.Application;

import androidx.work.WorkManager;

/**
 * @author chenguijian
 * @since 2023/11/29
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // 取消所有任务
        WorkManager.getInstance(this.getApplicationContext()).cancelAllWork();
    }
}
