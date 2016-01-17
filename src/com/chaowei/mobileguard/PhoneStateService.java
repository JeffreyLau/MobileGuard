package com.chaowei.mobileguard;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.android.internal.telephony.ITelephony;
import com.chaowei.mobileguard.db.dao.BlackNumberDao;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class PhoneStateService extends Service {
	protected static final String TAG = "PhoneStateService";
	private TelephonyManager mTelephonyManager;
	private final static Object mRingingLock = new Object();
	/** Used to alter media button redirection when the phone is ringing. */
	private boolean mIsRinging = false;
	private BlackNumberDao mBlackNumberDao;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		Log.i(TAG, "onCreate");
		mBlackNumberDao = new BlackNumberDao(this);
		mTelephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		mTelephonyManager.listen(mPhoneStateListener,
				PhoneStateListener.LISTEN_CALL_STATE);
		super.onCreate();

	}

	@Override
	public void onDestroy() {
		Log.i(TAG, "onDestroy");
		mTelephonyManager.listen(mPhoneStateListener,
				PhoneStateListener.LISTEN_NONE);
		stopSelf();
		super.onDestroy();

	}

	private PhoneStateListener mPhoneStateListener = new PhoneStateListener() {

		public void onCallStateChanged(int state, String incomingNumber) {
			if (state == TelephonyManager.CALL_STATE_RINGING) {
				Log.i(TAG, " CALL_STATE_RINGING");
				synchronized (mRingingLock) {
					mIsRinging = true;
					String mode = mBlackNumberDao.findMode(incomingNumber);
					if (mode.equals(MobileGuard.INTERCEPT_MODE_PHONE)) {
						Log.i(TAG, " INTERCEPT_MODE_PHONE");
						endCall();
					} else if (mode.equals(MobileGuard.INTERCEPT_MODE_SMS)) {
						Log.i(TAG, " INTERCEPT_MODE_SMS");
					} else {
						Log.i(TAG, " INTERCEPT_MODE_ALL");
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
}
