package com.academy.fundamentals.ex3.BackgroundServices;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;

public class ServiceHandler extends Handler {

    private Looper mServiceLooper;
    private IHardJobService mBroadcastMessages;

    public ServiceHandler(Looper aServiceLooper, IHardJobService aBroadcastMessages) {
        super(aServiceLooper);
        this.mServiceLooper = aServiceLooper;
        this.mBroadcastMessages = aBroadcastMessages;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (this.mBroadcastMessages == null) {
            return;
        }

        for (int i = 0; i <= 100 && !this.mBroadcastMessages.isDestroyed(); i++) {
            SystemClock.sleep(50);
            this.mBroadcastMessages.broadcastProgress(i);
        }

        // this.mBroadcastMessages.broadcastMessage("Finishing Hard Job Service, id: " +
        //         msg.arg1);

        // the msg.arg1 is the startId used in the onStartCommand,so we can track the running service here.
        this.mBroadcastMessages.stopService(msg.arg1);

    }
}
