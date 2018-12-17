package com.academy.fundamentals.ex3.BackgroundServices;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.academy.fundamentals.ex3.R;

public class BGServiceActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mProgressValue;
    private BackgroundProgressReceiver mBackgroundProgressReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bgservice);

        this.mProgressValue = findViewById(R.id.tv_progress_value);
        findViewById(R.id.btn_start_service).setOnClickListener(this);
        findViewById(R.id.btn_start_intent_service).setOnClickListener(this);
    }

    @Override
    public void onClick(View aView) {
        switch (aView.getId()) {
            case R.id.btn_start_service:
                Toast.makeText(this, "btn_start_service", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(aView.getContext(), HardJobService.class);
                super.startService(intent);
                break;

            case R.id.btn_start_intent_service:
                Toast.makeText(this, "btn_start_intent_service", Toast.LENGTH_SHORT).show();
                intent = new Intent(aView.getContext(), HardJobIntentService.class);
                startService(intent);
                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        this.subscribeForProgressUpdates();
    }

    @Override
    protected void onPause() {
        if (this.mBackgroundProgressReceiver != null) {
            super.unregisterReceiver(this.mBackgroundProgressReceiver);
        }

        super.onPause();
    }

    private void subscribeForProgressUpdates() {
        if (this.mBackgroundProgressReceiver == null) {
            this.mBackgroundProgressReceiver = new BackgroundProgressReceiver(this.mProgressValue);
        }
        IntentFilter progressUpdateActionFilter = new IntentFilter(BackgroundProgressReceiver.PROGRESS_UPDATE_ACTION);
        super.registerReceiver(this.mBackgroundProgressReceiver, progressUpdateActionFilter);
    }

}
