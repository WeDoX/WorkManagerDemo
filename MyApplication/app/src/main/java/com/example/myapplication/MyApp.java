package com.example.myapplication;

import android.app.Application;
import android.util.Log;


public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("ATU", "Worker 我被调用了");
        // 取消所有任务,App杀死后，Worker执行时，这里会被拉起。
        //WorkManager.getInstance(this.getApplicationContext()).cancelAllWork();
    }
}
