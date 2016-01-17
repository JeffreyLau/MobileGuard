package com.chaowei.mobileguard.activitys;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.ProtocolException;

import org.json.JSONException;
import org.json.JSONObject;

import com.chaowei.mobileguard.MGApplication;
import com.chaowei.mobileguard.MobileGuard;
import com.chaowei.mobileguard.R;
import com.chaowei.mobileguard.ui.IviewDialog;
import com.chaowei.mobileguard.utils.PackageInfoUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

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
import android.widget.TextView;
import android.widget.Toast;

public class SplashActivity extends Activity {
	private static final int SHOW_NOMAL_VERSION = 0x00;
	private static final int SHOW_UPDATE_DIALOG = 0x01;
	private static final int SHOW_REQUEST_ERROR = 0x02;
	private TextView tv_app_version;
	private static final String TAG = "SplashActivity";
	private SharedPreferences sharedPreferences;
	private MGApplication mGApplication;
	private ProgressDialog pd;
	private IviewDialog mIviewDialog;

	private long startTime;
	private long endTime;
	private long changeTime;
	private String downloadpath = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		mGApplication = (MGApplication) getApplication();
		sharedPreferences = getSharedPreferences(MobileGuard.SHARE_PREFERENCE,
				MODE_PRIVATE);
		tv_app_version = (TextView) findViewById(R.id.tv_app_version);
		tv_app_version.setText(PackageInfoUtils.getPackageVersion(this));
		if (sharedPreferences.getBoolean(MobileGuard.APP_AUTO_UPDATE, false)) {
			new ServerLetCallback().start();
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
		public void dispatchMessage(Message msg) {
			// TODO Auto-generated method stub

			switch (msg.what) {
			//
			case SHOW_NOMAL_VERSION:
				Toast.makeText(getBaseContext(), "版本一致无需更新:" + msg.obj,
						Toast.LENGTH_SHORT).show();
				loadHomeUI();
				break;
			case SHOW_UPDATE_DIALOG:
				showUpdateIviewDialog((String) msg.obj);
				break;
			case SHOW_REQUEST_ERROR:
				Toast.makeText(getBaseContext(), "错误码code:" + msg.obj,
						Toast.LENGTH_SHORT).show();
				loadHomeUI();
				break;
			default:
				break;
			}
			super.dispatchMessage(msg);
		}
	};

	private void downloadNewVersionApp() {
		pd = new ProgressDialog(this);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.show();
		HttpUtils httpUtils = new HttpUtils();
		File sdDir = Environment.getExternalStorageDirectory();
		File file = new File(sdDir, SystemClock.uptimeMillis() + ".apk");
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			httpUtils.download(downloadpath, file.getAbsolutePath(),
					new RequestCallBack<File>() {

						@Override
						public void onFailure(HttpException arg0, String arg1) {
							// TODO Auto-generated method stub
							pd.dismiss();
							loadHomeUI();
						}

						@Override
						public void onSuccess(ResponseInfo<File> arg0) {
							// TODO Auto-generated method stub
							pd.dismiss();
							Toast.makeText(getBaseContext(), "下载成功",
									Toast.LENGTH_SHORT).show();
							Log.i(TAG, "----進入升級界面-----");
							Intent intent = new Intent();
							intent.setAction("android.intent.action.VIEW");
							intent.addCategory("android.intent.category.DEFAULT");
							intent.setDataAndType(Uri.fromFile(arg0.result),
									"application/vnd.android.package-archive");
							startActivity(intent);
						}

						@Override
						public void onLoading(long total, long current,
								boolean isUploading) {
							// TODO Auto-generated method stub
							super.onLoading(total, current, isUploading);
							pd.setMax((int) total);
							pd.setProgress((int) current);
						}
					});
		}
		pd.dismiss();
	}

	public void showUpdateIviewDialog(String descrition) {

		IviewDialog.Builder builder = new IviewDialog.Builder(this);
		builder.setImageViewId(R.drawable.system_upgrade);
		builder.setTitle("升级提醒");
		builder.setMessage(descrition);
		builder.setPositiveButton("确定升级", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				downloadNewVersionApp();
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

	private class ServerLetCallback extends Thread {
		@Override
		public void run() {
			startTime = System.currentTimeMillis();
			// TODO Auto-generated method stub
			String descrition = null;
			String localVersion = null;
			String serverVersion = null;
			JSONObject jsonObject = null;
			InputStream inputStream = null;
			Message msg = Message.obtain();
			try {
				inputStream = PackageInfoUtils.getInputStreamFromUrl(
						R.string.update_url, SplashActivity.this);
				jsonObject = new JSONObject(
						PackageInfoUtils.setInputStreamToString(inputStream));
				serverVersion = jsonObject.getString("version");
				descrition = jsonObject.getString("descrition");

				localVersion = PackageInfoUtils
						.getPackageVersion(SplashActivity.this);
				downloadpath = jsonObject.getString("downloadpath");
				Log.i(TAG, "服务器版本：" + serverVersion);
				Log.i(TAG, "當前版本：" + localVersion);
				Log.i(TAG, "新版本的下载路径是:" + downloadpath);
				if (!serverVersion.equals(localVersion)
						&& serverVersion != null) {
					Log.i(TAG, "版本不一致需要更新");
					msg.what = SHOW_UPDATE_DIALOG;
					msg.obj = descrition;
				} else {
					Log.i(TAG, "版本一無需要更新");
					msg.what = SHOW_NOMAL_VERSION;
				}
			} catch (JSONException e) {
				Log.i(TAG, "JSONException");
				msg.what = SHOW_REQUEST_ERROR;
				msg.obj = "404";
				e.printStackTrace();
			} catch (ProtocolException e) {
				Log.i(TAG, "ProtocolException");
				msg.what = SHOW_REQUEST_ERROR;
				msg.obj = "405";
				e.printStackTrace();
			} catch (MalformedURLException e) {
				Log.i(TAG, "MalformedURLException");
				msg.what = SHOW_REQUEST_ERROR;
				msg.obj = "406";
				e.printStackTrace();
			} catch (IOException e) {
				Log.i(TAG, "IOException");
				msg.what = SHOW_REQUEST_ERROR;
				msg.obj = "407";
				e.printStackTrace();
			} finally {
				endTime = System.currentTimeMillis();
				changeTime = (endTime - startTime);
				if (changeTime < 2000)
					SystemClock.sleep(2000 - changeTime);
				mHandler.sendMessage(msg);
			}
		}
	}
}
