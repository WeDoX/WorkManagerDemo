package com.example.myapplication.keep_app_live;

import android.content.Context;
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.Worker

import androidx.work.WorkerParameters;
import java.util.concurrent.TimeUnit

class OneTimeWorker(context: Context, workerParams: WorkerParameters) : Worker(
    context,
    workerParams
) {
    override fun doWork(): Result {
        doPushPeriodicWork();
        return Result.success()
    }

    private fun doPushPeriodicWork() {
        val pushWorkRequest: PeriodicWorkRequest = PeriodicWorkRequest.Builder(
            PushWorker::class.java,
            15,
            TimeUnit.MINUTES
        ) // Constraints
            .build()
        //
        WorkManager
            .getInstance(this.applicationContext)
            .enqueue(pushWorkRequest)
    }
}