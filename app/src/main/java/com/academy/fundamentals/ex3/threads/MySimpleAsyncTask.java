package com.academy.fundamentals.ex3.threads;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

public class MySimpleAsyncTask {

    private IAsyncTaskEvents mIAsyncTaskEvents;
    private volatile boolean mCancelled = false;

    public MySimpleAsyncTask(IAsyncTaskEvents iAsyncTaskEvents) {
        mIAsyncTaskEvents = iAsyncTaskEvents;
    }

    void execute() {
        Looper mainLooper = Looper.getMainLooper();
        Handler handler = new Handler(mainLooper);

        MyRunnable myRunnable = new MyRunnable();
        handler.post(myRunnable);


    }

    void onPreExecute() {
        mIAsyncTaskEvents.onPreExecute();

    }

    void onPostExecute() {
        mIAsyncTaskEvents.onPostExecute();
    }

    void onProgressUpdate(Integer progress) {
        mIAsyncTaskEvents.onProgressUpdate(progress);
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


    public class MyRunnable implements Runnable {

        @Override
        public void run() {
            onPreExecute();

            doInBackground();

            onPostExecute();
        }
    }
}
