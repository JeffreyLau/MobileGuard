package com.chaowei.mobileguard.activitys;

import com.chaowei.mobileguard.MobileGuard;
import com.chaowei.mobileguard.R;
import com.chaowei.mobileguard.ui.SwitchImageView;
import com.lidroid.xutils.cache.LruDiskCache.Editor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class SettingActivity extends Activity {

	private SwitchImageView siv_update;
	private RelativeLayout rl_setting_update;
	private SharedPreferences sharedPreferences;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_seting);
		sharedPreferences = getSharedPreferences(MobileGuard.SHARE_PREFERENCE,
				MODE_PRIVATE);
		siv_update = (SwitchImageView) findViewById(R.id.siv_update);
		siv_update.setSwitchStatus(sharedPreferences.getBoolean(
				MobileGuard.APP_AUTO_UPDATE, true));
		rl_setting_update = (RelativeLayout) findViewById(R.id.rl_setting_update);
		rl_setting_update.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				siv_update.changeSwitchStatus();
				android.content.SharedPreferences.Editor editor = sharedPreferences
						.edit();
				editor.putBoolean(MobileGuard.APP_AUTO_UPDATE,
						siv_update.getSwitchStatus());
				editor.commit();
			}
		});
	}

}
