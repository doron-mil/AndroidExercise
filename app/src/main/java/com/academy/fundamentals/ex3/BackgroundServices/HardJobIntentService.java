package com.academy.fundamentals.ex3.BackgroundServices;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.Nullable;

public class HardJobIntentService extends IntentService {

    private static final String TAG = "HardJobIntentService";

    private boolean isDestroyed ;

    public HardJobIntentService() {
        super(HardJobIntentService.TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        this.isDestroyed = false;
        for (int i = 0; i <= 100 && !isDestroyed; i++) {
            SystemClock.sleep(30);
            Intent broadcastIntent = new Intent(BackgroundProgressReceiver.PROGRESS_UPDATE_ACTION);
            broadcastIntent.putExtra(BackgroundProgressReceiver.PROGRESS_VALUE_KEY, i);
            super.sendBroadcast(broadcastIntent);
        }
    }
}
