package com.example.myapplication.keep_app_live

import android.content.Context
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import java.util.Calendar
import java.util.concurrent.TimeUnit

object KeepAppLive {

    fun keepAppLive(context: Context) {
        ResetRule.handleInit(context)
        // 取消所有任务
        WorkManager.getInstance(context).cancelAllWork()
        //
        val currentDate = Calendar.getInstance()
        val dueDate = Calendar.getInstance()
        //
        for (index in 0..13) {
            var timeDiff: Long = 2
            if (index > 0) {
                dueDate.add(Calendar.MINUTE, 1)
                timeDiff = dueDate.timeInMillis - currentDate.timeInMillis
            }
            val dailyWorkRequest = OneTimeWorkRequest.Builder(OneTimeWorker::class.java)
                .setInitialDelay(timeDiff, TimeUnit.MILLISECONDS).build()
            WorkManager.getInstance(context).enqueue(dailyWorkRequest)
        }
    }
}