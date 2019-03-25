package com.ruben.clientandroid.Services;

import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.ruben.clientandroid.Models.Event;
import com.ruben.clientandroid.R;

public class NotificationService {
    private static final String NOTIFICATION_CHANNEL = "notification_channel";
    private static final String TAG = "PUSH_NOTIFICATION_TAG";
    private static NotificationService instance = null;
    private NotificationManagerCompat notificationManager;
    private Application application;
    private Activity activity;
    private int notificationIndex = 1;

    private NotificationService(Application application, Activity activity) {
        this.activity = activity;
        this.application = application;
        notificationManager = NotificationManagerCompat.from(application);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel chatChannel = new NotificationChannel(
                    NOTIFICATION_CHANNEL,
                    "New Chat Message",
                    NotificationManager.IMPORTANCE_HIGH
            );
            chatChannel.setDescription("A notification appears when another user sends a message");

            NotificationManager manager = application.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(chatChannel);
        }
    }

    public static NotificationService getInstance(Application application, Activity activity) {
        if (instance == null)
            instance = new NotificationService(application, activity);
        return instance;
    }

    public void sendNotification(Event event) {
        //  Intent resultIntent = new Intent(application, ChatActivity.class);
        //  resultIntent.putExtra("ContactObject", message.getSender());
        //  resultIntent.putExtra("message", message);

        //  TaskStackBuilder stackBuilder = TaskStackBuilder.create(application);
        //  stackBuilder.addNextIntent(resultIntent);
        //  PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(application, NOTIFICATION_CHANNEL)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(event.getName())
                .setContentText(event.getIdentifier())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();

        notificationManager.notify(notificationIndex, notification);
        notificationIndex++;
    }
}
