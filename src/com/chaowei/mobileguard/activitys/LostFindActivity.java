package com.chaowei.mobileguard.activitys;

import java.util.List;

import com.chaowei.mobileguard.MobileGuard;
import com.chaowei.mobileguard.R;
import com.chaowei.mobileguard.receiver.MGAdminReceiver;
import com.chaowei.mobileguard.ui.IviewDialog;
import com.chaowei.mobileguard.utils.PrivateInfoUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences.Editor;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class LostFindActivity extends Activity {
	private static final String TAG = "LostFindActivity";
	private ImageView iv_lostfind_statuslock;
	private TextView tv_lostfind_safenumber;
	private SharedPreferences mSharedPreferences;
	private DevicePolicyManager mDPM;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lost_find);

		iv_lostfind_statuslock = (ImageView) findViewById(R.id.iv_lostfind_statuslock);
		tv_lostfind_safenumber = (TextView) findViewById(R.id.tv_lostfind_safenumber);

		mDPM = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
		mSharedPreferences = getSharedPreferences(MobileGuard.SHARE_PREFERENCE,
				MODE_PRIVATE);
		if (mSharedPreferences.getBoolean(MobileGuard.APP_PROTECT, false)) {
			iv_lostfind_statuslock.setImageResource(R.drawable.lock);
		} else {
			iv_lostfind_statuslock.setImageResource(R.drawable.unlock);
		}
		tv_lostfind_safenumber.setText(mSharedPreferences.getString(
				MobileGuard.APP_SAFE_NUMBER, null));
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if (!isActiveAdmin()) {
			new Thread() {
				public void run() {
					SystemClock.sleep(3000);
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							activeAdminDialog();
						}
					});
				};
			}.start();

		}
	}

	public void reloadSetUpUi(View v) {
		Intent intent = new Intent(this, Setup1Activity.class);
		startActivity(intent);
	}

	public void changeProtectStatus(View v) {
		boolean protecting = mSharedPreferences.getBoolean(
				MobileGuard.APP_PROTECT, false);
		if (protecting) {
			iv_lostfind_statuslock.setImageResource(R.drawable.unlock);
			Editor editor = mSharedPreferences.edit();
			editor.putBoolean(MobileGuard.APP_PROTECT, false);
			editor.commit();
		} else {
			iv_lostfind_statuslock.setImageResource(R.drawable.lock);
			Editor editor = mSharedPreferences.edit();
			editor.putBoolean(MobileGuard.APP_PROTECT, true);
			editor.commit();
		}
	}

	private boolean isActiveAdmin() {
		ComponentName who = new ComponentName(this, MGAdminReceiver.class);
		return mDPM.isAdminActive(who);
	}

	private void activeAdminDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("溫馨提醒");
		builder.setMessage("開起手機衛士管理者,更好的保護您的手機");
		builder.setPositiveButton("確定", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				ComponentName who = new ComponentName(LostFindActivity.this,
						MGAdminReceiver.class);
				Intent intent = new Intent(
						DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
				intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, who);
				intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
						"請開起管理者權限,開起後可以鎖屏等");
				startActivity(intent);
			}
		});

		builder.setNegativeButton("取消", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		final AlertDialog mAlertDialog = builder.show();
	}
}
