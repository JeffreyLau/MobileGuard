package com.chaowei.mobileguard;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.ProtocolException;

import org.json.JSONException;
import org.json.JSONObject;

import com.chaowei.mobileguard.utils.PackageInfoUtils;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.telephony.SmsManager;
import android.util.Log;

public class MgGpsLocationService extends Service {
	private static final String TAG = "MgGpsLocationService";
	private LocationManager mLocationManager;
	private MgLocationListener mMgLocationListener;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.i(TAG, "MGApplicationService启动");
		mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		mMgLocationListener = new MgLocationListener();
		mLocationManager.requestLocationUpdates("gps", 0, 0,
				mMgLocationListener);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i(TAG, "MGApplicationService停止");
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return super.onStartCommand(intent, flags, startId);
	}

	private class MgLocationListener implements LocationListener {

		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			String latitude = "lo" + location.getLongitude() + "\n";
			String longitude = "la" + location.getLatitude();
			SmsManager.getDefault().sendTextMessage(
					getSharedPreferences(MobileGuard.SHARE_PREFERENCE,
							MODE_PRIVATE).getString(
							MobileGuard.APP_SAFE_NUMBER, null), null,
					latitude + longitude, null, null);
			mLocationManager.removeUpdates(mMgLocationListener);
			stopSelf();
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}

	}

}
