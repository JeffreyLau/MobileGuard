
package com.chaowei.mobileguard.activitys;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.ProtocolException;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback;
import org.xutils.common.Callback.CancelledException;
import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;

import com.chaowei.mobileguard.MGApplication;
import com.chaowei.mobileguard.MobileGuard;
import com.chaowei.mobileguard.R;
import com.chaowei.mobileguard.remote.DownloadCallback;
import com.chaowei.mobileguard.remote.DownloadInfo;
import com.chaowei.mobileguard.remote.UpgradeInfo;
import com.chaowei.mobileguard.remote.ServerRequestCallback;
import com.chaowei.mobileguard.tracker.AsyncResult;
import com.chaowei.mobileguard.tracker.AsyncTracker;
import com.chaowei.mobileguard.tracker.BaseTracker;
import com.chaowei.mobileguard.ui.IviewDialog;
import com.chaowei.mobileguard.utils.PackageInfoUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class SplashActivity extends Activity {
    private static final String TAG = "SplashActivity";

    private TextView tv_app_version;
    private SharedPreferences sharedPreferences;
    // private ProgressBar mProgressBar;
    private BaseTracker mBaseTracker;
    private String lVersion;
    private FrameLayout fl_update_opp;
    private ProgressBar pb_update_opp;
    private TextView tv_update_opp;
    private ProgressDialog pd_update_opp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sharedPreferences = getSharedPreferences(MobileGuard.SHARE_PREFERENCE,
                MODE_PRIVATE);
        tv_app_version = (TextView) findViewById(R.id.tv_app_version);
        lVersion = PackageInfoUtils
                .getPackageVersion(SplashActivity.this);
        tv_app_version.setText(lVersion);
        fl_update_opp = (FrameLayout) findViewById(R.id.fl_update_opp);
        pb_update_opp = (ProgressBar) findViewById(R.id.pb_update_opp);
        tv_update_opp = (TextView) findViewById(R.id.tv_update_opp);
        fl_update_opp.setVisibility(View.INVISIBLE);

        UpgradeInfo.getDefault().setlVersion(lVersion);

        mBaseTracker = AsyncTracker.getDefaultTracker();
        mBaseTracker.registerForRequestSuccess(mHandler,
                BaseTracker.SERVER_REQUEST_SUCCESS, null);
        mBaseTracker.registerForRequestFailure(mHandler,
                BaseTracker.SERVER_REQUEST_FAILURE, null);
        mBaseTracker.registerForDownloadOnSuccess(mHandler,
                BaseTracker.DOWNLOAD_ON_SUCCESS, null);
        mBaseTracker.registerForDownloadOnFinsh(mHandler,
                BaseTracker.DOWNLOAD_ON_FINSH, null);
        mBaseTracker.registerForDownloadOnLoading(mHandler,
                BaseTracker.DOWNLOAD_ON_LOAD, null);
        mBaseTracker.registerForDownloadOnError(mHandler,
                BaseTracker.DOWNLOAD_ON_ERROR, null);
        mBaseTracker.registerForDownloadOnStart(mHandler,
                BaseTracker.DOWNLOAD_ON_START, null);
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        if (sharedPreferences.
                getBoolean(MobileGuard.APP_AUTO_UPDATE, false)) {
            RequestParams params =
                    new RequestParams(UpgradeInfo.SERVER_URL_UPDATE_INFO);
            x.http().get(params, new ServerRequestCallback(this));
        } else {
            new Thread() {
                public void run() {
                    SystemClock.sleep(2000);
                    loadHomeUI();
                };
            }.start();
        }
    }

    private void loadHomeUI() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            AsyncResult ar;
            DownloadInfo mDownloadInfo;
            switch (msg.what) {
                case BaseTracker.SERVER_REQUEST_SUCCESS:
                    Log.i(TAG, "服务器连接成功");
                    Toast.makeText(SplashActivity.this, "服务器连接成功",
                            Toast.LENGTH_SHORT).show();
                    showDialogforUpgrade();
                    break;
                case BaseTracker.SERVER_REQUEST_FAILURE:
                    ar = (AsyncResult) msg.obj;
                    String coder = (String) ar.result;
                    Toast.makeText(SplashActivity.this, "error code:" + coder,
                            Toast.LENGTH_SHORT).show();
                    loadHomeUI();
                    break;

                case BaseTracker.DOWNLOAD_ON_SUCCESS:
                    Toast.makeText(SplashActivity.this, "下载成功",
                            Toast.LENGTH_SHORT).show();
                    break;
                case BaseTracker.DOWNLOAD_ON_FINSH:
                    ar = (AsyncResult) msg.obj;
                    mDownloadInfo = (DownloadInfo) ar.result;
                    fl_update_opp.setVisibility(View.INVISIBLE);
                    pd_update_opp.dismiss();
                    PackageInfoUtils.installApplication(SplashActivity.this,
                            mDownloadInfo.getFile());
//                    Intent intent = new Intent();
//                    intent.setAction("android.intent.action.VIEW");
//                    intent.addCategory("android.intent.category.DEFAULT");
//                    intent.setDataAndType(Uri.fromFile(mDownloadInfo.getFile()),
//                            "application/vnd.android.package-archive");
//                    startActivity(intent);

                    break;
                case BaseTracker.DOWNLOAD_ON_START:
                    pd_update_opp = new ProgressDialog(SplashActivity.this);
                    pd_update_opp.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    pd_update_opp.show();
                    fl_update_opp.setVisibility(View.VISIBLE);
                    break;

                case BaseTracker.DOWNLOAD_ON_LOAD:
                    ar = (AsyncResult) msg.obj;
                    mDownloadInfo = (DownloadInfo) ar.result;
                    pd_update_opp.setMax((int) mDownloadInfo.getTotal());
                    pd_update_opp.setProgress((int) mDownloadInfo.getCurrent());
                    pb_update_opp.setMax((int) mDownloadInfo.getTotal());
                    pb_update_opp.setProgress((int) mDownloadInfo.getCurrent());
                    break;

                case BaseTracker.DOWNLOAD_ON_ERROR:
                    ar = (AsyncResult) msg.obj;
                    fl_update_opp.setVisibility(View.INVISIBLE);
                    pd_update_opp.dismiss();
                    String codeu = (String) ar.result;
                    Toast.makeText(SplashActivity.this, "error code:" + codeu,
                            Toast.LENGTH_SHORT).show();
                    loadHomeUI();
                    break;

                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public void startUpgradeDownload(Context context) {
        if (!lVersion.equals(UpgradeInfo.getDefault().getsVersion())) {
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                File sdDir = Environment.getExternalStorageDirectory();
                String fileSavePath = new File(sdDir, SystemClock.uptimeMillis()
                        + ".apk").getAbsolutePath();
                RequestParams params = new RequestParams(UpgradeInfo.getDefault().getdPath());
                params.setAutoResume(true);
                // params.setAutoRename(true);
                params.setSaveFilePath(fileSavePath);
                // 自定义线程池
                // params.setExecutor(executor);
                params.setCancelFast(true);
                DownloadCallback callback = new DownloadCallback(context);
                Callback.Cancelable cancelable = x.http().get(params, callback);
                callback.setCancelable(cancelable);
            }
        }

    }

    public void showDialogforUpgrade() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
        builder.setTitle("升级提醒");
        builder.setMessage(UpgradeInfo.getDefault().getDesc());
        builder.setPositiveButton("确定升级", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
                startUpgradeDownload(SplashActivity.this);
            }
        });

        builder.setNegativeButton("下次再说", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
                loadHomeUI();
            }
        });
        builder.show();
    }

    @Override
    public void finish() {
        mBaseTracker.unregisterForRequestSuccess(mHandler);
        mBaseTracker.unregisterForRequestFailure(mHandler);

        mBaseTracker.unregisterForDownloadOnStart(mHandler);
        mBaseTracker.unregisterForDownloadOnSuccess(mHandler);
        mBaseTracker.unregisterForDownloadOnFinsh(mHandler);
        mBaseTracker.unregisterForDownloadOnLoading(mHandler);
        mBaseTracker.unregisterForDownloadOnError(mHandler);
        super.finish();
    }
}
