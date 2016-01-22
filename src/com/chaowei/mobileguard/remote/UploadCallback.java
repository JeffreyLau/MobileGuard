
package com.chaowei.mobileguard.remote;

import java.io.File;

import org.xutils.common.Callback;
import org.xutils.common.Callback.Cancelable;
import org.xutils.common.Callback.CancelledException;

import com.chaowei.mobileguard.tracker.AsyncResult;
import com.chaowei.mobileguard.tracker.AsyncTracker;

import android.content.Context;
import android.util.Log;

public class UploadCallback implements Callback.CommonCallback<File>,
        Callback.ProgressCallback<File>,
        Callback.Cancelable {
    private static final String TAG = "UploadCallback";
    private boolean cancelled = false;
    private Cancelable cancelable;

    public UploadCallback() {
        // TODO Auto-generated constructor stub
    }
    
    public void setCancelable(Cancelable cancelable) {
        this.cancelable = cancelable;
    }   

    @Override
    public void cancel() {
        // TODO Auto-generated method stub
        Log.i(TAG, "cancel");
        cancelled = true;
        if (cancelable != null) {
            cancelable.cancel();
        }
    }

    @Override
    public boolean isCancelled() {
        // TODO Auto-generated method stub
         Log.i(TAG, "isCancelled");
        // return false;
        return cancelled;
    }

    @Override
    public void onLoading(long total, long current, boolean isDownloading) {
        // TODO Auto-generated method stub
         Log.i(TAG, "onLoading:" + "total = " + total + "current = " + current
         + "isDownloading = "
         + isDownloading);

    }

    @Override
    public void onStarted() {
        // TODO Auto-generated method stub
        Log.i(TAG, "onStarted");

    }

    @Override
    public void onWaiting() {
        // TODO Auto-generated method stub
        Log.i(TAG, "onWaiting");
    }

    @Override
    public void onCancelled(CancelledException cex) {
        // TODO Auto-generated method stub
        Log.i(TAG, "onCancelled");
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        // TODO Auto-generated method stub
        Log.i(TAG, "onError");

    }

    @Override
    public void onFinished() {
        // TODO Auto-generated method stub
        Log.i(TAG, "onFinished");
        cancelled = false;

    }

    @Override
    public void onSuccess(File result) {
        // TODO Auto-generated method stub
        Log.i(TAG, "onSuccess File = " + result.getAbsolutePath());

    }

}
