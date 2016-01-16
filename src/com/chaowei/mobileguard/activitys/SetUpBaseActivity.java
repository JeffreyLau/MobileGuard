package com.chaowei.mobileguard.activitys;

import com.chaowei.mobileguard.MobileGuard;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public abstract class SetUpBaseActivity extends Activity {

	public abstract void startNextUi();

	public abstract void startPreviousUi();

	private GestureDetector mGestureDetector;
	private static final String TAG = "SetUpBaseActivity";
	protected SharedPreferences sharedPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		sharedPreferences = getSharedPreferences(MobileGuard.SHARE_PREFERENCE,
				MODE_PRIVATE);
		mGestureDetector = new GestureDetector(this,
				new MGAppSetupGestureListener());
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		mGestureDetector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}

	/**
	 * This is here so we can identify single tap events and set the selected
	 * day correctly
	 */
	private class MGAppSetupGestureListener extends
			GestureDetector.SimpleOnGestureListener {

		/**
		 * Notified when a tap occurs with the up {@link MotionEvent} that
		 * triggered it.
		 * 
		 * @param e
		 *            The up motion event that completed the first tap
		 * @return true if the event is consumed, else false
		 */
		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			return true;
		}

		/**
		 * Notified of a fling event when it occurs with the initial on down
		 * {@link MotionEvent} and the matching up {@link MotionEvent}. The
		 * calculated velocity is supplied along the x and y axis in pixels per
		 * second.
		 * 
		 * @param e1
		 *            The first down motion event that started the fling.
		 * @param e2
		 *            The move motion event that triggered the current onFling.
		 * @param velocityX
		 *            The velocity of this fling measured in pixels per second
		 *            along the x axis.
		 * @param velocityY
		 *            The velocity of this fling measured in pixels per second
		 *            along the y axis.
		 * @return true if the event is consumed, else false
		 */
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			// TODO Auto-generated method stub

			if (Math.abs(velocityX) < 200) {
				Log.i(TAG, "移動速度太慢,無效操作");
				return true;
			}
			if (Math.abs(e1.getRawY() - e2.getRawY()) > 80) {
				Log.i(TAG, "往Ｙ方向過濾掉此事件,無效操作");
				return true;
			}
			if (e1.getRawX() - e2.getRawX() > 150) {
				Log.i(TAG, "說明向左滑動,顯示下一個界面");
				// startNextActivity();
				startNextUi();
			}

			if ((e2.getRawX() - e1.getRawX()) > 200) {
				Log.i(TAG, "說明向右滑動,顯示上一個界面");
				startPreviousUi();
			}
			return super.onFling(e1, e2, velocityX, velocityY);
		}
	}

	public void nextOnclick(View v) {
		startNextUi();
	}

	public void previousOnclick(View v) {
		startPreviousUi();
	}
	
	public void startNewActivityAndFinsh(Class<?> cls) {
		Intent intent = new Intent(this, cls);
		startActivity(intent);
		finish();
	}
}
