package com.academy.fundamentals.ex3.threads;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

public class MySimpleAsyncTask extends Thread{

    private IAsyncTaskEvents mIAsyncTaskEvents;
    private volatile boolean mCancelled = false;

    public MySimpleAsyncTask(IAsyncTaskEvents iAsyncTaskEvents) {
        mIAsyncTaskEvents = iAsyncTaskEvents;
    }

    void execute() {
        onPreExecute();

        doInBackground();

        onPostExecute();

        // Looper mainLooper = Looper.getMainLooper();
        // Handler handler = new Handler(mainLooper);
        //
        // MyRunnable myRunnable = new MyRunnable();
        // handler.post(myRunnable);


    }

    void onPreExecute() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mIAsyncTaskEvents.onPreExecute();
            }
        };
        runOnUiThread(runnable);



    }

    void onPostExecute() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mIAsyncTaskEvents.onPostExecute();
            }
        };
        runOnUiThread(runnable);
    }

    void onProgressUpdate(final Integer progress) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mIAsyncTaskEvents.onProgressUpdate(progress);
            }
        };
        runOnUiThread(runnable);

    }


    public boolean isCancelled() {
        return this.mCancelled;
    }

    protected void doInBackground() {
        int end = 10;
        for (int i = 0; i <= end; i++) {
            if (isCancelled()) {
                return;
            }

            onProgressUpdate(i);

            SystemClock.sleep(500);
        }
    }

    public void cancel() {
        this.mCancelled = true;
    }

    private void runOnUiThread(Runnable runnable) {
        new Handler(Looper.getMainLooper()).post(runnable);
    }

    public class MyRunnable implements Runnable {

        @Override
        public void run() {
            onPreExecute();

            doInBackground();


            onPostExecute();
        }
    }
}
