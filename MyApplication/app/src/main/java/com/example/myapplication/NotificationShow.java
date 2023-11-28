package com.example.myapplication;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationChannelCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

/**
 * @author chenguijian
 * @since 2023/11/28
 */
public class NotificationShow {

    public static void sendNotification(Context context, String responseBody) {
        String chanelId = "keep_live";
        NotificationChannelCompat channelCompat = new NotificationChannelCompat.Builder(chanelId, NotificationManagerCompat.IMPORTANCE_MAX)
                .setName("拉活App通知渠道")
                .build();
        //
        Notification notification = new NotificationCompat.Builder(context, chanelId)
                .setContentTitle("你有一条新消息待处理")
                .setContentText(responseBody)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(startAppIntent(context))
                .build();
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        managerCompat.createNotificationChannel(channelCompat);
        managerCompat.notify(1, notification);
    }

    private static PendingIntent startAppIntent(Context context) {
        PendingIntent pendingIntent = PendingIntent.getActivity(context, -1, new Intent(context, MainActivity.class), PendingIntent.FLAG_IMMUTABLE);
        return pendingIntent;
    }
}
