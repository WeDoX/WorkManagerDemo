package com.example.myapplication.keep_app_live;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.myapplication.NotificationShow;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PushWorker extends Worker {
    public PushWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        Response response = null;
        try {
            Log.e("ATU", "Worker 开始执行");
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();
            Request request = new Request.Builder()
                    .url("http://120.78.120.117/APush/push_new_info.php")
                    .build();
            response = client.newCall(request).execute();
            int statusCode = response.code(); // 获取响应状态码
            String responseBody = response.body().string(); // 获取响应体内容
            NotificationShow.sendNotification(getApplicationContext(), responseBody);
            Log.e("ATU", "Worker success="+getId());
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ATU", "Worker error="+getId()+" e=" + e.toString());
            return Result.failure();
        } finally {
            if (null != response) {
                try {
                    response.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            ResetRule.INSTANCE.handleLogic(getApplicationContext());
        }
    }
}