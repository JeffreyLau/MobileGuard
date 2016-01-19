package com.chaowei.mobileguard.activitys;

import com.chaowei.mobileguard.MGApplication;
import com.chaowei.mobileguard.MgCallLocationService;
import com.chaowei.mobileguard.MobileGuard;
import com.chaowei.mobileguard.MgInCallStateService;
import com.chaowei.mobileguard.R;
import com.chaowei.mobileguard.ui.SwitchImageView;
import com.chaowei.mobileguard.utils.ServiceStatusUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
	private SwitchImageView siv_showlocation;
	private RelativeLayout rl_setting_showlocation;
	private RelativeLayout rl_setting_showlocation_style;

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
		siv_showlocation = (SwitchImageView) findViewById(R.id.siv_showlocation);

		siv_update.setSwitchStatus(sharedPreferences.getBoolean(
				MobileGuard.APP_AUTO_UPDATE, false));

		siv_showlocation.setSwitchStatus(ServiceStatusUtils.isServiceRuning(
				this, MobileGuard.INCALL_LOCATION_SERVICE));
		siv_intercep.setSwitchStatus(ServiceStatusUtils.isServiceRuning(this,
				MobileGuard.INCALL_STATE_SERVICE));

		rl_setting_showlocation = (RelativeLayout) findViewById(R.id.rl_setting_showlocation);
		rl_setting_intercep = (RelativeLayout) findViewById(R.id.rl_setting_intercep);
		rl_setting_update = (RelativeLayout) findViewById(R.id.rl_setting_update);
		rl_setting_showlocation_style = (RelativeLayout) findViewById(R.id.rl_setting_showlocation_style);

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
				Intent intent = new Intent(SettingActivity.this,
						MgInCallStateService.class);
				if (status) {
					startService(intent);
				} else {
					stopService(intent);
				}
			}
		});

		rl_setting_showlocation.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				siv_showlocation.changeSwitchStatus();
				boolean status = siv_showlocation.getSwitchStatus();
				Intent intent = new Intent(SettingActivity.this,
						MgCallLocationService.class);
				if (status) {
					startService(intent);
				} else {
					stopService(intent);
				}
			}
		});

		rl_setting_showlocation_style.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(
						SettingActivity.this);
				builder.setTitle("归属地查询风格");
				builder.setSingleChoiceItems(MGApplication.bgNames,
						sharedPreferences.getInt(
								MobileGuard.SHOW_LOCATION_WHICHSTYLE, 0),
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								Editor editor = sharedPreferences.edit();
								editor.putInt(
										MobileGuard.SHOW_LOCATION_WHICHSTYLE,
										which);
								editor.commit();
								dialog.dismiss();
							}
						});
				builder.show();
			}
		});

	}

}
