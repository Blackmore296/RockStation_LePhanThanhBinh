package com.example.appnghenhac.Background;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

//DANGKY NOTIFICATION HIEN THI O THANH THONG BAO TREN DEVICE//
public class ChanelNoti extends Application {
    public static final String channel = "channelid";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(channel,"channelmusic", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setSound(null,null);
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null){
                manager.createNotificationChannel(notificationChannel);
            }
        }
    }
}
