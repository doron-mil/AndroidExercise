package com.academy.fundamentals.ex3.BackgroundServices;

import android.app.Service;
import android.content.Intent;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;

public class HardJobService extends Service implements IHardJobService {

    private static final String TAG = "HardJobService";

    private ServiceHandler mServiceHandler;
    private Looper mServiceLooper;
    private boolean isDestroyed = false;


    @Override
    public void onCreate() {
        super.onCreate();

        // To avoid cpu-blocking, we create a background handler to run our service
        HandlerThread thread = new HandlerThread(HardJobService.TAG, Process.THREAD_PRIORITY_BACKGROUND);
        // start the new handler thread
        thread.start();

        this.mServiceLooper = thread.getLooper();
        // start the service using the background handler
        this.mServiceHandler = new ServiceHandler(this.mServiceLooper, this);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.isDestroyed = false;
        // call a new service handler. The service ID can be used to identify the service
        Message message = this.mServiceHandler.obtainMessage();
        message.arg1 = startId;
        this.mServiceHandler.sendMessage(message);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        this.isDestroyed = false;
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void broadcastMessage(String aMessage) {
        Intent intent = new Intent(BackgroundProgressReceiver.PROGRESS_UPDATE_ACTION);
        intent.putExtra(BackgroundProgressReceiver.PROGRESS_VALUE_KEY, aMessage);
        sendBroadcast(intent);
    }

    @Override
    public void broadcastProgress(int aProgress) {
        Intent intent = new Intent(BackgroundProgressReceiver.PROGRESS_UPDATE_ACTION);
        intent.putExtra(BackgroundProgressReceiver.PROGRESS_VALUE_KEY, aProgress);
        sendBroadcast(intent);

    }

    @Override
    public boolean isDestroyed() {
        return this.isDestroyed;
    }

    @Override
    public void stopService(int startId) {
        super.stopSelf(startId);
    }
}
