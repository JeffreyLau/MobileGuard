package com.chaowei.mobileguard.receiver;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.chaowei.mobileguard.MGLocationService;
import com.chaowei.mobileguard.MobileGuard;
import com.chaowei.mobileguard.R;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SmsReceiver extends BroadcastReceiver {

	private static final String TAG = "SmsReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Bundle bundle = intent.getExtras();
		if (bundle != null) {
			Object[] pdus = (Object[]) bundle.get("pdus");
			SmsMessage[] messages = new SmsMessage[pdus.length];
			for (int i = 0; i < pdus.length; i++) {
				messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
				String body = messages[i].getMessageBody();
				String sender = messages[i].getOriginatingAddress();
				Date date = new Date(messages[i].getTimestampMillis());
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String sendTime = sdf.format(date);
				if (body.equals(MobileGuard.APP_FUNCTION_GPS_LOCATION)) {
					Log.i(TAG, "返回手機的位置");
					Intent mIntent = new Intent(context, MGLocationService.class);
					context.startService(mIntent);
					abortBroadcast();
				} else if (body.equals(MobileGuard.APP_FUNCTION_ALARM_PLAY)) {
					Log.i(TAG, "播放報警聲音");
					MediaPlayer mMediaPlayer = MediaPlayer.create(context,
							R.raw.wszdan);
					// mMediaPlayer.setLooping(true);
					mMediaPlayer.start();
					abortBroadcast();
				} else if (body.equals(MobileGuard.APP_FUNCTION_WIPE_DATA)) {
					Log.i(TAG, "清除數據");
					DevicePolicyManager mDevicePolicyManager = (DevicePolicyManager) context
							.getSystemService(Context.DEVICE_POLICY_SERVICE);
					mDevicePolicyManager
							.wipeData(DevicePolicyManager.WIPE_EXTERNAL_STORAGE);
					abortBroadcast();
				} else if (body.equals(MobileGuard.APP_FUNCTION_LOCK_SCREEN)) {
					Log.i(TAG, "立即鎖屏");
					DevicePolicyManager mDevicePolicyManager = (DevicePolicyManager) context
							.getSystemService(Context.DEVICE_POLICY_SERVICE);
					//mDevicePolicyManager.resetPassword("123", 0);
					mDevicePolicyManager.lockNow();
					abortBroadcast();
				}
			}
		}
	}

}
