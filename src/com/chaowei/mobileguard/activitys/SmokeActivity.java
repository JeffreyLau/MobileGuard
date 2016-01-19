package com.chaowei.mobileguard.activitys;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.chaowei.mobileguard.MobileGuard;
import com.chaowei.mobileguard.R;
import com.chaowei.mobileguard.db.dao.BlackNumberDao;

import android.app.Activity;
import android.app.PendingIntent.OnFinished;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

public class SmokeActivity extends Activity {
	private static final String TAG = "SmokeActivity";
	private ImageView iv_whirlpool;
	private ImageView iv_whirlpoolStat2;
	private ImageView iv_smoke_down;
	private ImageView iv_smoke_up;
	private AnimationDrawable whirlAnimation;
	private SmokeBroadCastReceiver mSmokeBroadCastReceiver;
	private WindowManager wm;
	Point screenSize = new Point();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_whirlpool);
		mSmokeBroadCastReceiver = new SmokeBroadCastReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(MobileGuard.MOTION_EVENT_ROKET_UP);
		filter.addAction(MobileGuard.MOTION_EVENT_ROKET_BOTTOM);
		filter.addAction(MobileGuard.MOTION_EVENT_ROKET_PULL);
		filter.addAction(MobileGuard.MOTION_EVENT_ROKET_MOVE);
		filter.addAction(MobileGuard.MOTION_EVENT_ROKET_COMPLETE);
		registerReceiver(mSmokeBroadCastReceiver, filter);
		wm = (WindowManager) getSystemService(WINDOW_SERVICE);
		wm.getDefaultDisplay().getSize(screenSize);
		iv_whirlpool = (ImageView) findViewById(R.id.iv_whirlpool);
		iv_whirlpoolStat2 = (ImageView) findViewById(R.id.iv_whirlpoolStat2);
		iv_smoke_down = (ImageView) findViewById(R.id.iv_smoke_down);
		iv_smoke_up = (ImageView) findViewById(R.id.iv_smoke_up);

		whirlAnimation = (AnimationDrawable) iv_whirlpool.getBackground();
		whirlAnimation.start();
		// iv_whirlpool.setVisibility(View.INVISIBLE);
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		unregisterReceiver(mSmokeBroadCastReceiver);
		super.finish();
	}

	private class SmokeBroadCastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction()
					.equals(MobileGuard.MOTION_EVENT_ROKET_BOTTOM)) {
				whirlAnimation.stop();
				iv_whirlpool.setVisibility(View.INVISIBLE);
				iv_whirlpoolStat2.setVisibility(View.VISIBLE);
			} else if (intent.getAction().equals(
					MobileGuard.MOTION_EVENT_ROKET_MOVE)) {
				whirlAnimation.start();
				iv_whirlpoolStat2.setVisibility(View.INVISIBLE);
				iv_whirlpool.setVisibility(View.VISIBLE);
			} else if (intent.getAction().equals(
					MobileGuard.MOTION_EVENT_ROKET_PULL)) {
				whirlAnimation.stop();
				iv_whirlpool.setVisibility(View.INVISIBLE);
				iv_whirlpoolStat2.setVisibility(View.INVISIBLE);
				iv_smoke_down.setVisibility(View.VISIBLE);
				iv_smoke_up.setVisibility(View.VISIBLE);
			} else if (intent.getAction().equals(
					MobileGuard.MOTION_EVENT_ROKET_COMPLETE)) {
				whirlAnimation.stop();
				iv_whirlpool.setVisibility(View.INVISIBLE);
				iv_whirlpoolStat2.setVisibility(View.INVISIBLE);
				iv_smoke_down.setVisibility(View.INVISIBLE);
				iv_smoke_up.setVisibility(View.INVISIBLE);
				finish();
			}
		}
	}
}
