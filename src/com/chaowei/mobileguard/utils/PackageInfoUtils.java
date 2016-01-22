package com.chaowei.mobileguard.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.chaowei.mobileguard.MGApplication;
import com.chaowei.mobileguard.R;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources.NotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

public class PackageInfoUtils {

	private static final String TAG = "MGApplicationUtil";

	public static String getPackageVersion(Context context) {
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			return info.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "version error";
	}

	public static InputStream getInputStreamFromString(String string) {
		if ((string != null) && !string.trim().equals("")) {
			ByteArrayInputStream bis = new ByteArrayInputStream(
					string.getBytes());
			return bis;
		}
		return null;
	}

	public static InputStream getInputStreamFromUrl(int urlId, Context context)
			throws ProtocolException, MalformedURLException, NotFoundException,
			IOException {
		// TODO Auto-generated method stub
		String urlAddress = context.getString(urlId);
		URL url = new URL(urlAddress);
		HttpURLConnection httpURLConnection = (HttpURLConnection) url
				.openConnection();
		httpURLConnection.setRequestMethod("GET");
		httpURLConnection.setReadTimeout(2000);
		int returnCode = httpURLConnection.getResponseCode();
		httpURLConnection.getInputStream();
		if (returnCode == 200) {
			Log.i(TAG, "连接服务器成功");
			return httpURLConnection.getInputStream();
		} else if (returnCode == 200) {
			Log.i(TAG, "404 ERROR");
		} else {
			Log.i(TAG, "NOt Found ERROR");
		}
		return null;
	}

	public static String setInputStreamToString(InputStream is)
			throws IOException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;// read data of InputStream to buffer;
		while ((len = is.read(buffer)) != -1) {
			baos.write(buffer, 0, len);// write data of buffer to baos
		}
		return baos.toString();

	}


}
