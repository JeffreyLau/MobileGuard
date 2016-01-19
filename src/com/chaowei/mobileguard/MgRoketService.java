package com.chaowei.mobileguard;

import com.chaowei.mobileguard.activitys.SmokeActivity;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;

public class MgRoketService extends Service {
	private static final String TAG = "MgRoketService";
	private WindowManager wm;
	private WindowManager.LayoutParams params;
	private AnimationDrawable rocketAnimation;
	private ImageView rocketImage;
	private View view;
	private float startX;
	private float startY;
	private float currentX;
	private float currentY;
	private float changeX;
	private float changeY;
	Point screenSize = new Point();
	private final static Object mMoveLock = new Object();
	private boolean mIsMoving = false;
	private Handler mHandler = new Handler();

	private void windInit(Context context) {
		wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getSize(screenSize);
		view = View.inflate(context, R.layout.layout_roket, null);
		rocketImage = (ImageView) view.findViewById(R.id.iv_roket);
		rocketAnimation = (AnimationDrawable) rocketImage.getBackground();
		params = new LayoutParams();
		params.gravity = Gravity.LEFT + Gravity.TOP;
		params.height = WindowManager.LayoutParams.WRAP_CONTENT;
		params.width = WindowManager.LayoutParams.WRAP_CONTENT;
		params.format = PixelFormat.TRANSLUCENT;
		params.type = WindowManager.LayoutParams.TYPE_PRIORITY_PHONE;
		params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
				| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
		view.setOnTouchListener(new ViewOnTouchListener());
		wm.addView(view, params);
	}

	private boolean viewInXcenter(float x) {
		if (x >= (screenSize.x / 4) && x <= (screenSize.x * 3 / 4))
			return true;
		else
			return false;
	}

	private class ViewOnTouchListener implements OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				rocketAnimation.start();
				startX = event.getRawX();
				startY = event.getRawY();
				break;
			case MotionEvent.ACTION_MOVE:
				currentX = event.getRawX();
				currentY = event.getRawY();
				changeX = (currentX - startX);
				changeY = (currentY - startY);
				updateWindowLocation(changeX, changeY);
				// 一定記得重新賦值起點位置
				startX = event.getRawX();
				startY = event.getRawY();
				startSmokeActivity();
				if (((screenSize.y - currentY) < 180)
						&& viewInXcenter(currentX)) {
					synchronized (mMoveLock) {
						if (!mIsMoving) {
							mIsMoving = true;
							processBroadcast(
									MobileGuard.MOTION_EVENT_ROKET_BOTTOM,
									currentX, currentY);// ANIMATION
						}
					}
				} else {
					synchronized (mMoveLock) {
						if (mIsMoving) {
							mIsMoving = false;
							processBroadcast(
									MobileGuard.MOTION_EVENT_ROKET_MOVE,
									currentX, currentY);
						}

					}
				}
				break;
			case MotionEvent.ACTION_UP:
				rocketAnimation.stop();
				if (((screenSize.y - currentY) < 180)
						&& viewInXcenter(currentX)) {
					processBroadcast(MobileGuard.MOTION_EVENT_ROKET_PULL,
							currentX, currentY);

					new Thread() {
						public void run() {
							for (int i = 0; i < 15; i++) {
								params.y -= 20;
								SystemClock.sleep(30);
								mHandler.post(new Runnable() {

									@Override
									public void run() {
										// TODO Auto-generated method stub
										wm.updateViewLayout(view, params);
									}
								});
							}
							processBroadcast(MobileGuard.MOTION_EVENT_ROKET_COMPLETE,
									currentX, currentY);
						};

					}.start();
				}

				break;
			default:
				break;
			}

			return true;
		}

		private void processBroadcast(String action, Float x, Float y) {
			Intent intent = new Intent();
			intent.putExtra("currentX", x);
			intent.putExtra("currentY", y);
			intent.setAction(action);
			sendBroadcast(intent);
		}

		private void startSmokeActivity() {
			Intent intent = new Intent(MgRoketService.this, SmokeActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
		}

	}

	private void updateWindowLocation(float dX, float dY) {
		params.x += dX;// 修改Ｘ軸上的位置
		params.y += dY;// 修改Ｙ軸上的位置
		wm.updateViewLayout(view, params);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		windInit(this);
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
