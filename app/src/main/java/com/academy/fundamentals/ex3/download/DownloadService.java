package com.academy.fundamentals.ex3.download;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.academy.fundamentals.ex3.R;

public class DownloadService extends Service {

    public static final String URL = "URL";
    public static final int ONGOING_NOTIFICATION_ID = 43222;
    private static final String CHANNEL_DEFAULT_IMPORTANCE = "Channel";
    public static final String BROADCAST_ACTION = "com.academy.fundamentals.DOWNLOAD_COMPLETE";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static void startService(Activity activity, String url) {
        Intent intent = new Intent(activity, DownloadService.class);
        intent.putExtra(URL, url);
        activity.startService(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        this.startForeground();
        String url = intent.getStringExtra(URL);

        this.startDownloadThread( url );

        return START_STICKY;
    }

    private void startDownloadThread(String url) {
        new DownloadThread(url, new DownloadThread.DownloadCallBack() {
            @Override
            public void onProgressUpdate(int percent) {
                // Log.e("doron - ccc", "" + percent);
                updateNotification(percent);
            }
            @Override
            public void onDownloadFinished(String filePath) {
                sendBroadcastMsgDownloadComplete(filePath);
                stopSelf();

            }
            @Override
            public void onError(String error) {
                Log.e("Error with DownloadThread:", "" +  error);
            }
        }).start();
    }

    private void sendBroadcastMsgDownloadComplete(String filePath) {
        Intent intent = new Intent(BROADCAST_ACTION);
        intent.putExtra(DownloadActivity.ARG_FILE_PATH, filePath);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

    }

    private void startForeground() {
        this.createNotificationChannel();
        Notification notification = this.createNotification(0);
        startForeground(ONGOING_NOTIFICATION_ID, notification);
    }

    private void updateNotification(int progress) {
        NotificationManager notificationManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            Notification notification = createNotification(progress);
            notificationManager.notify(ONGOING_NOTIFICATION_ID, notification);
        }
    }


    private Notification createNotification(int progress) {
        Intent notificationIntent = new Intent(this, DownloadActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        String progressStr = getString(R.string.notification_message, progress);

        return new NotificationCompat.Builder(this, CHANNEL_DEFAULT_IMPORTANCE)
                .setContentTitle(getText(R.string.notification_title))
                .setContentText(progressStr)
                .setOnlyAlertOnce(true)
                .setSmallIcon(android.R.drawable.stat_sys_upload)
                .setProgress(100, progress, false)
                .setContentIntent(pendingIntent)
                .build();

    }

    private void createNotificationChannel() {
        CharSequence name = getString(R.string.channel_name); // The user-visible name
        String description = getString(R.string.channel_description); // The user-visible description
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel mChannel = new NotificationChannel(CHANNEL_DEFAULT_IMPORTANCE, name, importance);
        mChannel.setDescription(description);
        mChannel.enableLights(true);
        mChannel.setLightColor(Color.RED);
        mChannel.enableVibration(true);
        mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        NotificationManager notificationManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(mChannel);
    }

}
