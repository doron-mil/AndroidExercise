package com.academy.fundamentals.ex3.BackgroundServices;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

public class BackgroundProgressReceiver extends BroadcastReceiver {

    public static final String PROGRESS_UPDATE_ACTION = "PROGRESS_UPDATE_ACTION";
    public static final String PROGRESS_VALUE_KEY = "PROGRESS_VALUE_KEY";
    public static final String SERVICE_STATUS = "SERVICE_STATUS";

    private TextView mProgressValue;

    public BackgroundProgressReceiver(TextView aProgressValue) {
        this.mProgressValue = aProgressValue;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int progress = intent.getIntExtra(BackgroundProgressReceiver.PROGRESS_VALUE_KEY, -1);

        this.mProgressValue.setText( progress + "%");
    }
}
