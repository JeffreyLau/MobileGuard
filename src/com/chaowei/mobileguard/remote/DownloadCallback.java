
package com.chaowei.mobileguard.remote;

import java.io.File;

import org.xutils.common.Callback;

import com.chaowei.mobileguard.tracker.AsyncResult;
import com.chaowei.mobileguard.tracker.AsyncTracker;

import android.content.Context;
import android.util.Log;

public class DownloadCallback implements Callback.CommonCallback<File>,
        Callback.ProgressCallback<File>,
        Callback.Cancelable {

    private static final String TAG = "DownloadCallback";
    private boolean cancelled = false;
    private Cancelable cancelable;
    private Context mContext;
    private DownloadInfo mDownloadInfo;

    public DownloadCallback() {

    }

    public DownloadCallback(Context context) {
        this.mContext = context;
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
        // Log.i(TAG, "isCancelled");
        // return false;
        return cancelled;
    }

    @Override
    public void onLoading(long total, long current, boolean isDownloading) {
        // TODO Auto-generated method stub
        // Log.i(TAG, "onLoading:" + "total = " + total + "current = " + current
        // + "isDownloading = "
        // + isDownloading);
        if (mDownloadInfo != null) {
            mDownloadInfo.setTotal(total);
            mDownloadInfo.setCurrent(current);
            mDownloadInfo.setDownloading(isDownloading);
            AsyncTracker.getDefaultTracker().notifyDownloadOnLoadingRegistrants(
                    new AsyncResult(null, mDownloadInfo, null));
        }
    }

    @Override
    public void onStarted() {
        // TODO Auto-generated method stub
        Log.i(TAG, "onStarted");
        mDownloadInfo = new DownloadInfo();
        AsyncTracker.getDefaultTracker().notifyDownloadOnStartRegistrants(
                new AsyncResult(null, null, null));
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
        AsyncTracker.getDefaultTracker().notifyDownloadOnStartRegistrants(
                new AsyncResult(null, "407", null));
    }

    @Override
    public void onFinished() {
        // TODO Auto-generated method stub
        Log.i(TAG, "onFinished");
        cancelled = false;
        if(mDownloadInfo !=null) 
            AsyncTracker.getDefaultTracker().notifyDownloadOnFinshRegistrants(
                    new AsyncResult(null, mDownloadInfo, null));
    }

    @Override
    public void onSuccess(File result) {
        // TODO Auto-generated method stub
        //Log.i(TAG, "onSuccess File = " + result.getAbsolutePath());
        if(mDownloadInfo !=null)  {
            mDownloadInfo.setFile(result);
            AsyncTracker.getDefaultTracker().notifyDownloadOnSuccessRegistrants(
                    new AsyncResult(null, mDownloadInfo, null));  
        }
    }

}
