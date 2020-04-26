package com.lzf.wanandroidapp.worker;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.WorkFinishedCallback;
import androidx.work.Worker;

public class WanWorker extends Worker {
    String TAG = "WanWorker";

    @NonNull
    @Override
    public Result doWork() {
        return Result.SUCCESS;
    }

    @Override
    public void onStopped(boolean cancelled) {
        super.onStopped(cancelled);
        Log.d(TAG, "onStopped: ");
    }
}
