package com.chaowei.mobileguard;

import com.chaowei.mobileguard.db.dao.NumberAddressDao;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MgCallLocationService extends Service {

	private static final String TAG = "MgInCallLocationService";
	private TelephonyManager mTelephonyManager;
	private CallStateListener mCallStateListener;
	private OutCallRecevier mOutCallRecevier;
	private ToastView mToastView;
	private SharedPreferences sharedPreferences;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		Log.i(TAG, "onCreate");
		sharedPreferences = getSharedPreferences(MobileGuard.SHARE_PREFERENCE,
				MODE_PRIVATE);
		mCallStateListener = new CallStateListener();
		mOutCallRecevier = new OutCallRecevier();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_NEW_OUTGOING_CALL);
		registerReceiver(mOutCallRecevier, filter);
		mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		mTelephonyManager.listen(mCallStateListener,
				PhoneStateListener.LISTEN_CALL_STATE);
		mToastView = new ToastView();
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		unregisterReceiver(mOutCallRecevier);
		mTelephonyManager.listen(mCallStateListener,
				PhoneStateListener.LISTEN_NONE);
		mCallStateListener = null;
		super.onDestroy();
	}

	private class CallStateListener extends PhoneStateListener {
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			// TODO Auto-generated method stub

			switch (state) {
			case TelephonyManager.CALL_STATE_RINGING:
				String location = NumberAddressDao
						.findNumberAddress(incomingNumber);
				mToastView.makeToastView(MgCallLocationService.this, location);
				break;
			case TelephonyManager.CALL_STATE_IDLE:
				// case TelephonyManager.CALL_STATE_OFFHOOK:
				mToastView.dismiss();
				break;
			default:
				break;
			}
			super.onCallStateChanged(state, incomingNumber);
		}
	}

	private class OutCallRecevier extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String number = getResultData();
			String location = NumberAddressDao.findNumberAddress(number);
			mToastView.makeToastView(MgCallLocationService.this, location);
		}

	}

	public void showMyToast(CharSequence text) {
		TextView view = new TextView(this);
		view.setText(text);
		view.setTextColor(Color.RED);
		view.setTextSize(20);
		WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		final WindowManager.LayoutParams params = new LayoutParams();
		params.height = WindowManager.LayoutParams.WRAP_CONTENT;
		params.width = WindowManager.LayoutParams.WRAP_CONTENT;
		params.format = PixelFormat.TRANSLUCENT;
		params.type = WindowManager.LayoutParams.TYPE_TOAST;
		params.setTitle("Toast");
		params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
				| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
				| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
		wm.addView(view, params);
	}

	public class ToastView {

		private View view;
		private WindowManager wm;
		private WindowManager.LayoutParams params;
		private TextView tv_toast_location;

		// private ImageView iv_toast_location;

		private void windInit(Context context) {
			wm = (WindowManager) context
					.getSystemService(Context.WINDOW_SERVICE);
			params = new LayoutParams();
			params.height = WindowManager.LayoutParams.WRAP_CONTENT;
			params.width = WindowManager.LayoutParams.WRAP_CONTENT;
			params.format = PixelFormat.TRANSLUCENT;
			params.type = WindowManager.LayoutParams.TYPE_TOAST;
			params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
					| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
					| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
		}

		public void makeToastView(Context context, CharSequence text) {
			// TODO Auto-generated constructor stub
			windInit(context);
			view = View.inflate(context, R.layout.toast_location, null);
			view.setBackgroundResource(MGApplication.bgIcons[sharedPreferences
					.getInt(MobileGuard.SHOW_LOCATION_WHICHSTYLE, 0)]);
			tv_toast_location = (TextView) view
					.findViewById(R.id.tv_toast_location);
			// iv_toast_location = (ImageView) view
			// .findViewById(R.id.iv_toast_location);
			tv_toast_location.setText(text);
			wm.addView(view, params);
		}

		public void dismiss() {
			if (view != null && wm != null) {
				wm.removeView(view);
				view = null;
				wm = null;
			}

		}
	}

}
