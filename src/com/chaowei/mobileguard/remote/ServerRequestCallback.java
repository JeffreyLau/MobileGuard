
package com.chaowei.mobileguard.remote;

import java.io.File;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import com.chaowei.mobileguard.tracker.AsyncResult;
import com.chaowei.mobileguard.tracker.AsyncTracker;

import android.content.Context;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;

public class ServerRequestCallback implements Callback.CommonCallback<JSONObject> {

    private static final String TAG = "ServerRequestCallback";
    private Context mContext;

    public ServerRequestCallback(Context context) {
        this.mContext = context;
    }

    @Override
    public void onCancelled(CancelledException cex) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onFinished() {
        // TODO Auto-generated method stub
        Log.i(TAG,"服务器连接onFinished");
    }

    @Override
    public void onSuccess(JSONObject jsonObject) {
        // TODO Auto-generated method stub
        try {
            String sVersion = jsonObject.getString("version");
            // String lVersion = RemoteServerLet.getDefault().getlVersion();
            String dPath = jsonObject.getString("downloadpath");
            String desc = jsonObject.getString("descrition");
            UpgradeInfo.getDefault().setDesc(desc);
            UpgradeInfo.getDefault().setdPath(dPath);
            UpgradeInfo.getDefault().setsVersion(sVersion);
            AsyncTracker.getDefaultTracker().notifyRequestSuccessRegistrants(
                    new AsyncResult(null, null, null));
            Log.i(TAG,"服务器连接成功");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            AsyncTracker.getDefaultTracker().notifyRequestFailureRegistrants(
                    new AsyncResult(null, "406", null));
        }

    }
}
