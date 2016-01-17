package com.chaowei.mobileguard.receiver;

import com.chaowei.mobileguard.MGApplication;
import com.chaowei.mobileguard.MobileGuard;
import com.chaowei.mobileguard.PhoneStateService;
import com.chaowei.mobileguard.activitys.SettingActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;

public class MGBootReceiver extends BroadcastReceiver {

	private static final String TAG = "MGBootReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub

		// 开机运行电话监听服务
		if (!MGApplication.isServiceRunning(context,
				MobileGuard.PHONE_STATE_SERVICE)) {
			Intent mIntent = new Intent(context, PhoneStateService.class);
			context.startService(mIntent);
		}

		SharedPreferences sharedPreferences = context.getSharedPreferences(
				MobileGuard.SHARE_PREFERENCE, Context.MODE_PRIVATE);
		boolean protecting = sharedPreferences.getBoolean(
				MobileGuard.APP_PROTECT, false);

		if (protecting) {
			String bindsim = sharedPreferences.getString(
					MobileGuard.APP_BIND_SIM, null);
			TelephonyManager mTelephonyManager = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			String simSerialNumber = mTelephonyManager.getSimSerialNumber();
			if (!bindsim.equals(simSerialNumber)) {
				// Log.i(TAG, "序列號不一致您的手機可能被盜");
				String safeNumber = sharedPreferences.getString(
						MobileGuard.APP_SAFE_NUMBER, null);
				SmsManager smsManager = SmsManager.getDefault();
				smsManager.sendTextMessage(safeNumber, null, "您的手機可能已丟", null,
						null);
			}
		}
	}

}
