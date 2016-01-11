package com.chaowei.mobileguard.activitys;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.ProtocolException;

import org.json.JSONException;
import org.json.JSONObject;

import com.chaowei.mobileguard.MGApplication;
import com.chaowei.mobileguard.R;
import com.chaowei.mobileguard.utils.PackageInfoUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class SplashActivity extends Activity {

	private TextView tv_app_version;
	private static final String TAG = "SplashActivity";
	private static final int SHOW_UPDATE_DIALOG = 0x00;
	private static final int SHOW_UPDATE_ERROR = 0x01;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		tv_app_version = (TextView) findViewById(R.id.tv_app_version);
		String appVersion = PackageInfoUtil.getPackageVersion(this);
		tv_app_version.setText(appVersion);
		new Thread(new CheckVersionTask()).start();
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case SHOW_UPDATE_DIALOG:
				showUpdateDialog((String) msg.obj);
				break;
			case SHOW_UPDATE_ERROR:
				Toast.makeText(getApplication(), "错误码code:" + msg.obj,
						Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		};
	};

	public void showUpdateDialog(String descrition) {
		AlertDialog.Builder builder = new Builder(SplashActivity.this);
		builder.setTitle("升级提醒");
		builder.setMessage(descrition);
		builder.setPositiveButton("确定升级", null);
		builder.setNegativeButton("下次再说", null);
		builder.show();
	}

	private class CheckVersionTask implements Runnable {

		@Override
		public void run() {
			try {
				InputStream inputStream = ((MGApplication) getApplication())
						.getInputStreamFromUrl(R.string.update_url);
				String string = ((MGApplication) getApplication())
						.setInputStreamToString(inputStream);
				JSONObject jsonObject = new JSONObject(string);
				String serverVersion = jsonObject.getString("version");
				String descrition = jsonObject.getString("descrition");
				Log.i(TAG, "服务器版本：" + serverVersion);
				String localVersion = PackageInfoUtil
						.getPackageVersion(getApplication());
				if (serverVersion.equals(localVersion)) {
					Log.i(TAG, "版本一致不需要更新");
				} else {
					Log.i(TAG, "版本不一致需要更新");
					Message msg = Message.obtain();
					msg.what = SHOW_UPDATE_DIALOG;
					msg.obj = descrition;
					handler.sendMessage(msg);
				}
			} catch (JSONException e) {
				Message msg = Message.obtain();
				msg.what = SHOW_UPDATE_ERROR;
				msg.obj = "404";
				handler.sendMessage(msg);
				e.printStackTrace();
			} catch (ProtocolException e) {
				Log.i(TAG, "版本不一致需要更新");
				Message msg = Message.obtain();
				msg.what = SHOW_UPDATE_ERROR;
				msg.obj = "405";
				handler.sendMessage(msg);
				e.printStackTrace();
			} catch (MalformedURLException e) {
				Log.i(TAG, "版本不一致需要更新");
				Message msg = Message.obtain();
				msg.what = SHOW_UPDATE_ERROR;
				msg.obj = "406";
				handler.sendMessage(msg);
				e.printStackTrace();
			} catch (IOException e) {
				Log.i(TAG, "版本不一致需要更新");
				Message msg = Message.obtain();
				msg.what = SHOW_UPDATE_ERROR;
				msg.obj = "407";
				handler.sendMessage(msg);
				e.printStackTrace();
			}
		}
	}

}
