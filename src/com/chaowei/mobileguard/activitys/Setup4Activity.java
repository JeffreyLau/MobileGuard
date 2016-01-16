package com.chaowei.mobileguard.activitys;

import com.chaowei.mobileguard.MobileGuard;
import com.chaowei.mobileguard.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class Setup4Activity extends SetUpBaseActivity {

	private CheckBox cb_setup4_status;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup4);
		cb_setup4_status = (CheckBox) findViewById(R.id.cb_setup4_status);
		cb_setup4_status.setChecked(sharedPreferences.getBoolean(
				MobileGuard.APP_PROTECT, false));
		cb_setup4_status
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						Editor editor = sharedPreferences.edit();
						editor.putBoolean(MobileGuard.APP_PROTECT, isChecked);
						editor.commit();
					}
				});
	}

	public void completeOnclick(View v) {
		Editor editor = sharedPreferences.edit();
		editor.putBoolean(MobileGuard.APP_CONFIGED, true);
		editor.commit();
		startNextUi();
	}

	@Override
	public void startNextUi() {
		// TODO Auto-generated method stub
		startNewActivityAndFinsh(LostFindActivity.class);
		overridePendingTransition(R.anim.anim_next_in, R.anim.anim_next_out);
	}

	@Override
	public void startPreviousUi() {
		// TODO Auto-generated method stub
		startNewActivityAndFinsh(Setup3Activity.class);
		overridePendingTransition(R.anim.anim_previous_in,
				R.anim.anim_previous_out);
	}

}
