package com.chaowei.mobileguard;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import android.app.Application;
import android.content.res.Resources.NotFoundException;
import android.util.Log;

/**
 * 存放共有的数据
 * 
 * @author Jeffrey
 * 
 */
public class MGApplication extends Application {
	private static final String TAG = "MGApplication";

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

		Log.i(TAG, "MGApplication Creat");
	}

	public InputStream getInputStreamFromString(String string) {
		if ((string != null) && !string.trim().equals("")) {
			ByteArrayInputStream bis = new ByteArrayInputStream(
					string.getBytes());
			return bis;
		}
		return null;
	}

	public InputStream getInputStreamFromUrl(int urlId)
			throws ProtocolException, MalformedURLException, NotFoundException,
			IOException {
		// TODO Auto-generated method stub
		String urlAddress = getString(urlId);
		URL url = new URL(urlAddress);
		HttpURLConnection httpURLConnection = (HttpURLConnection) url
				.openConnection();
		httpURLConnection.setRequestMethod("GET");
		httpURLConnection.setReadTimeout(5000);
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

	public String setInputStreamToString(InputStream is) throws IOException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;// read data of InputStream to buffer;
		while ((len = is.read(buffer)) != -1) {
			baos.write(buffer, 0, len);// write data of buffer to baos
		}
		return baos.toString();

	}

}
