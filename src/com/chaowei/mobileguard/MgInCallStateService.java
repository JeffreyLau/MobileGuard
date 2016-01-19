package com.chaowei.mobileguard;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import com.android.internal.telephony.ITelephony;
import com.chaowei.mobileguard.db.dao.BlackNumberDao;

import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.telephony.PhoneStateListener;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class MgInCallStateService extends Service {
	protected static final String TAG = "MgInCallStateService";
	private TelephonyManager mTelephonyManager;
	private final static Object mRingingLock = new Object();
	/** Used to alter media button redirection when the phone is ringing. */
	private boolean mIsRinging = false;
	private BlackNumberDao mBlackNumberDao;
	private SmsBroadCastReceiver mSmsBroadCastReceiver;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		Log.i(TAG, "onCreate");
		mSmsBroadCastReceiver = new SmsBroadCastReceiver();
		mBlackNumberDao = new BlackNumberDao(this);
		mTelephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		mTelephonyManager.listen(mPhoneStateListener,
				PhoneStateListener.LISTEN_CALL_STATE);
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.provider.Telephony.SMS_RECEIVED");
		filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
		registerReceiver(mSmsBroadCastReceiver, filter);
		super.onCreate();

	}

	@Override
	public void onDestroy() {
		Log.i(TAG, "onDestroy");
		mTelephonyManager.listen(mPhoneStateListener,
				PhoneStateListener.LISTEN_NONE);
		unregisterReceiver(mSmsBroadCastReceiver);
		stopSelf();
		super.onDestroy();

	}

	private PhoneStateListener mPhoneStateListener = new PhoneStateListener() {

		public void onCallStateChanged(int state, String incomingNumber) {
			if (state == TelephonyManager.CALL_STATE_RINGING) {
				// Log.i(TAG, " CALL_STATE_RINGING");
				synchronized (mRingingLock) {
					mIsRinging = true;
					String mode = mBlackNumberDao.findMode(incomingNumber);
					if (mode.equals(MobileGuard.INTERCEPT_MODE_PHONE)
							|| mode.equals(MobileGuard.INTERCEPT_MODE_ALL)) {
						Log.i(TAG, " 發現黑名單電話攔截");
						endCall();
						deleteCallLog(incomingNumber);
					}
				}
			} else if ((state == TelephonyManager.CALL_STATE_OFFHOOK)
					|| (state == TelephonyManager.CALL_STATE_IDLE)) {
				Log.i(TAG, " CALL_STATE_RINGING");
				synchronized (mRingingLock) {
					mIsRinging = false;
				}
			}
		};
	};

	private void endCall() {
		// ITelephony.Stub.asInterface(ServiceManager.getService(Context.TELEPHONY_SERVICE));
		try {
			// 1.获取类
			Class clazz = getClassLoader().loadClass(
					"android.os.ServiceManager");
			// ２.获取该类中的方法
			Method method = clazz.getDeclaredMethod("getService", String.class);
			// 3.执行
			IBinder binder = (IBinder) method.invoke(null,
					Context.TELEPHONY_SERVICE);
			ITelephony iTelephony = ITelephony.Stub.asInterface(binder);
			iTelephony.endCall();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void deleteCallLog(final String incomingNumber) {
		final ContentResolver contentResolver = getContentResolver();
		final Uri url = Uri.parse("content://call_log/calls");
		contentResolver.registerContentObserver(url, true, new ContentObserver(
				new Handler()) {
			@Override
			public void onChange(boolean selfChange) {
				// TODO Auto-generated method stub
				contentResolver.delete(url, "number=?",
						new String[] { incomingNumber });
				super.onChange(selfChange);
			}
		});
	}

	private class SmsBroadCastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			Log.i(TAG, "--------收到短信------");
			Object[] objs = (Object[]) intent.getExtras().get("pdus");
			for (Object object : objs) {
				SmsMessage smsMessage = SmsMessage
						.createFromPdu((byte[]) object);
				// 獲取發信人
				String sender = smsMessage.getOriginatingAddress();
				String body = smsMessage.getMessageBody();
				Date date = new Date(smsMessage.getTimestampMillis());
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String sendTime = sdf.format(date);
				String mode = mBlackNumberDao.findMode(sender);
				if (mode.equals(MobileGuard.INTERCEPT_MODE_SMS)
						|| mode.equals(MobileGuard.INTERCEPT_MODE_ALL)) {
					Log.i(TAG, " 發現黑名單短信攔截");
					Toast.makeText(context,
							"收到 " + sender + "發來的短信,內容爲:" + body,
							Toast.LENGTH_LONG).show();
//					Intent xintent = new Intent(Intent.ACTION_MAIN);
//					ComponentName cn = new ComponentName(
//							"com.android.settings",
//							"com.android.settings.Settings");
//					xintent.setComponent(cn);
//					xintent.putExtra(":android:show_fragment",
//							"com.android.settings.applications.AppOpsSummary");
//					xintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//					startActivity(xintent);

					abortBroadcast();// 取消廣播就可以攔截短信
				}
			}

		}

	}
}
