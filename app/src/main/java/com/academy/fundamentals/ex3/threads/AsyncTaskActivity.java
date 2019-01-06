package com.academy.fundamentals.ex3.threads;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.academy.fundamentals.ex3.R;

public class AsyncTaskActivity extends AppCompatActivity implements IAsyncTaskEvents{

    private static FragmentManager mFragmentManager;
    private CounterFragment mThreadsFragment;
    private CounterAsyncTask mAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);

        AsyncTaskActivity.mFragmentManager = getSupportFragmentManager();//Get Fragment Manager
        if (this.mThreadsFragment == null) {
            this.mThreadsFragment = CounterFragment.newInstance("aa" ,"bbb");
            Bundle data = new Bundle();//Use bundle to pass data
            data.putString(CounterFragment.FRAGMENT_TYPE, getString(R.string.async_task_activity));//put string, int, etc in bundle with a key value
            this.mThreadsFragment.setArguments(data);//Finally set argument bundle to fragment
            AsyncTaskActivity.mFragmentManager.beginTransaction().replace(R.id.fragment, mThreadsFragment).commit();//now replace the argument fragment
        }
    }


    @Override
    public void createAsyncTask() {
        Toast.makeText(this, getString(R.string.msg_oncreate), Toast.LENGTH_SHORT).show();
        mAsyncTask = new CounterAsyncTask(this);

    }

    @Override
    public void startAsyncTask() {
        if ((mAsyncTask == null) || (mAsyncTask.isCancelled())) {
            Toast.makeText(this, R.string.msg_should_create_task, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.msg_onstart), Toast.LENGTH_SHORT).show();
            mAsyncTask.execute(10);
        }
    }

    @Override
    public void cancelAsyncTask() {
        if (mAsyncTask == null) {
            Toast.makeText(this, R.string.msg_should_create_task, Toast.LENGTH_SHORT).show();
        } else {
            mAsyncTask.cancel(true);
        }

    }

    @Override
    public void onPreExecute() {
        Toast.makeText(this, getString(R.string.msg_preexecute), Toast.LENGTH_SHORT).show();
        mThreadsFragment.updateFragmentText("");
        Log.d("*****", "onPreExecute");

    }

    @Override
    public void onPostExecute() {
        Toast.makeText(this, getString(R.string.msg_postexecute), Toast.LENGTH_SHORT).show();
        mThreadsFragment.updateFragmentText(getString(R.string.done));
        mAsyncTask = null;
        Log.d("*****", "onPostExecute Done!");
    }

    @Override
    public void onProgressUpdate(Integer integer) {
        mThreadsFragment.updateFragmentText(String.valueOf(integer));
        Log.d("*****", "onProgressUpdate: " + integer);

    }

    @Override
    public void onCancel() {

    }

    @Override
    protected void onStop() {
        this.cancelAsyncTask();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        this.cancelAsyncTask();
        super.onDestroy();
    }

}
