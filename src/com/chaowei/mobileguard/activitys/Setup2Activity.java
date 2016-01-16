package com.chaowei.mobileguard.activitys;

import com.chaowei.mobileguard.MobileGuard;
import com.chaowei.mobileguard.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Setup2Activity extends SetUpBaseActivity {

	private RelativeLayout rl_setup2_bind_sim;
	private ImageView iv_setup2_bind_status;
	private TelephonyManager mTelephonyManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup2);
		// TODO Auto-generated method stub
		mTelephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		iv_setup2_bind_status = (ImageView) findViewById(R.id.iv_setup2_bind_status);
		String bindsim = sharedPreferences.getString(MobileGuard.APP_BIND_SIM,
				null);
		if (TextUtils.isEmpty(bindsim))
			iv_setup2_bind_status.setImageResource(R.drawable.unlock);
		else
			iv_setup2_bind_status.setImageResource(R.drawable.lock);
		rl_setup2_bind_sim = (RelativeLayout) findViewById(R.id.rl_setup2_bind_sim);
		rl_setup2_bind_sim.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String bindsim = sharedPreferences.getString(
						MobileGuard.APP_BIND_SIM, null);
				if (TextUtils.isEmpty(bindsim)) {
					String simSerialNumber = mTelephonyManager
							.getSimSerialNumber();
					Editor editor = sharedPreferences.edit();
					editor.putString(MobileGuard.APP_BIND_SIM, simSerialNumber);
					editor.commit();
					iv_setup2_bind_status.setImageResource(R.drawable.lock);
				} else {
					Editor editor = sharedPreferences.edit();
					editor.putString(MobileGuard.APP_BIND_SIM, null);
					editor.commit();
					iv_setup2_bind_status.setImageResource(R.drawable.unlock);
				}

			}
		});
	}

	@Override
	public void startNextUi() {
		// TODO Auto-generated method stub
		String bindsim = sharedPreferences.getString(MobileGuard.APP_BIND_SIM,
				null);
		if (TextUtils.isEmpty(bindsim)) {
			Toast.makeText(this, "使用手机防盗功能必须先绑定SIM卡", Toast.LENGTH_SHORT)
					.show();
			return;
		}

		startNewActivityAndFinsh(Setup3Activity.class);
		overridePendingTransition(R.anim.anim_next_in, R.anim.anim_next_out);
	}

	@Override
	public void startPreviousUi() {
		// TODO Auto-generated method stub
		startNewActivityAndFinsh(Setup1Activity.class);
		overridePendingTransition(R.anim.anim_previous_in,
				R.anim.anim_previous_out);
	}
}
