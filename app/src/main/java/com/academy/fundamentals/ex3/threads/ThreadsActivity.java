package com.academy.fundamentals.ex3.threads;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.academy.fundamentals.ex3.R;

public class ThreadsActivity extends AppCompatActivity implements IAsyncTaskEvents{

    private static FragmentManager mFragmentManager;
    private CounterFragment mThreadsFragment;
    private MySimpleAsyncTask mSimpleAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threads);

        mFragmentManager = getSupportFragmentManager();//Get Fragment Manager

        if (mThreadsFragment == null) {
            mThreadsFragment = new CounterFragment();//Get Fragment Instance
            Bundle data = new Bundle();//Use bundle to pass data
            data.putString(CounterFragment.FRAGMENT_TYPE, getString(R.string.handler_exe_activity));//put string, int, etc in bundle with a key value
            mThreadsFragment.setArguments(data);//Finally set argument bundle to fragment
            mFragmentManager.beginTransaction().replace(R.id.fragment, mThreadsFragment).commit();//now replace the argument fragment
        }

    }

    @Override
    public void createAsyncTask() {
        Toast.makeText(this, getString(R.string.msg_thread_oncreate), Toast.LENGTH_SHORT).show();
        mSimpleAsyncTask = new MySimpleAsyncTask(this);

    }

    @Override
    public void startAsyncTask() {
        if ((mSimpleAsyncTask == null) || (mSimpleAsyncTask.isCancelled())) {
            Toast.makeText(this, R.string.msg_should_create_task, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.msg_thread_onstart), Toast.LENGTH_SHORT).show();
            mSimpleAsyncTask.start();
        }
    }

    @Override
    public void cancelAsyncTask() {
        if (mSimpleAsyncTask == null) {
            Toast.makeText(this, R.string.msg_should_create_task, Toast.LENGTH_SHORT).show();
        } else {
            mSimpleAsyncTask.cancel();
        }
    }

    @Override
    public void onPreExecute() {
        Toast.makeText(this, getString(R.string.msg_preexecute), Toast.LENGTH_SHORT).show();
        mThreadsFragment.updateFragmentText(getString(R.string.msg_onstart));
    }

    @Override
    public void onPostExecute() {
        Toast.makeText(this, getString(R.string.msg_postexecute), Toast.LENGTH_SHORT).show();
        mThreadsFragment.updateFragmentText(getString(R.string.done));
        mSimpleAsyncTask = null;
    }

    @Override
    public void onProgressUpdate(Integer integer) {
        Toast.makeText(this, "zzzzz" + integer, Toast.LENGTH_SHORT).show();
        mThreadsFragment.updateFragmentText(String.valueOf(integer));
    }

    @Override
    public void onCancel() {
        Toast.makeText(this, getString(R.string.msg_thread_oncancel), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        this.cancelAsyncTask();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        this.cancelAsyncTask();
        super.onStop();
    }

}
