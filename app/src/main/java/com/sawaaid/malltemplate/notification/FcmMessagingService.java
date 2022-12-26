package com.sawaaid.malltemplate.notification;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.sawaaid.malltemplate.R;
import com.sawaaid.malltemplate.data.DataApp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class FcmMessagingService extends FirebaseMessagingService {


    PendingIntent pendingIntent;
    String title, body, activity;

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        DataApp.pref().setFcmRegId(s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Map<String, String> data = remoteMessage.getData();

        try {
            JSONObject jsonObject = new JSONObject(data);

            title = jsonObject.getString("title");
            body = jsonObject.getString("body");
            activity = jsonObject.getString("activity");

            showNotification(body, title, activity);
        } catch (JSONException ignored) {

        }


    }

    private void showNotification(String body, String title, String activity) {


        String NOTIFICATION_CHANNEL_ID = "SHAM";


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        builder.setContentTitle(title);
        builder.setContentText(body);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setDefaults(android.app.Notification.DEFAULT_LIGHTS);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_ID, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }
        int unique_id = (int) System.currentTimeMillis();
        notificationManager.notify(unique_id, builder.build());


    }
}
//AAAAo9r31eQ:APA91bGRQqWRnOdRY5JCTobkYB_GmZ2NB46jzb1I1I4kAhlpwTDoC7jgterz5hLTrL9-WYDpWiuSY_N5d3wXqlLDcJkzOOGL0wTI2UMpCE8_FV48WYpiES8RseHkzCVGGILgPIwRJfKT