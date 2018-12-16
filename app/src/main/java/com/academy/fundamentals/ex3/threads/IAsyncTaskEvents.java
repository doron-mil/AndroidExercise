package com.academy.fundamentals.ex3.threads;

public interface IAsyncTaskEvents {

    void createAsyncTask();
    void startAsyncTask();
    void cancelAsyncTask();

    void onPreExecute();
    void onPostExecute();
    void onProgressUpdate(Integer integer);
    void onCancel();
}
