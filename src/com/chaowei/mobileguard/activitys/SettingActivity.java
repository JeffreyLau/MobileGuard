package com.chaowei.mobileguard.activitys;

import com.chaowei.mobileguard.MGApplication;
import com.chaowei.mobileguard.MobileGuard;
import com.chaowei.mobileguard.PhoneStateService;
import com.chaowei.mobileguard.R;
import com.chaowei.mobileguard.ui.SwitchImageView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

public class SettingActivity extends Activity {

	protected static final String TAG = "SettingActivity";
	private SwitchImageView siv_update;
	private RelativeLayout rl_setting_update;
	private SwitchImageView siv_intercep;
	private RelativeLayout rl_setting_intercep;
	private SharedPreferences sharedPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_seting);
		sharedPreferences = getSharedPreferences(MobileGuard.SHARE_PREFERENCE,
				MODE_PRIVATE);
		siv_update = (SwitchImageView) findViewById(R.id.siv_update);
		siv_intercep = (SwitchImageView) findViewById(R.id.siv_intercept);

		siv_intercep.setSwitchStatus(sharedPreferences.getBoolean(
				MobileGuard.APP_AUTO_INTERCEPT, false));

		siv_update.setSwitchStatus(sharedPreferences.getBoolean(
				MobileGuard.APP_AUTO_UPDATE, false));

		rl_setting_intercep = (RelativeLayout) findViewById(R.id.rl_setting_intercep);
		rl_setting_update = (RelativeLayout) findViewById(R.id.rl_setting_update);

		rl_setting_update.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				siv_update.changeSwitchStatus();
				Editor editor = sharedPreferences.edit();
				editor.putBoolean(MobileGuard.APP_AUTO_UPDATE,
						siv_update.getSwitchStatus());
				editor.commit();
			}
		});

		rl_setting_intercep.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				siv_intercep.changeSwitchStatus();
				boolean status = siv_intercep.getSwitchStatus();
				if (status) {
					Log.i(TAG, "开启电话监听服务");
					Intent intent = new Intent(SettingActivity.this,
							PhoneStateService.class);
					startService(intent);
				} else {
					Log.i(TAG, "关闭电话监听服务");
					Intent intent = new Intent(SettingActivity.this,
							PhoneStateService.class);
					stopService(intent);
				}
				Editor editor = sharedPreferences.edit();
				editor.putBoolean(MobileGuard.APP_AUTO_INTERCEPT, status);
				editor.commit();
			}
		});
	}

}
