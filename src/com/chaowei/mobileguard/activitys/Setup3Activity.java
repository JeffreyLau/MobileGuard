package com.chaowei.mobileguard.activitys;

import com.chaowei.mobileguard.MobileGuard;
import com.chaowei.mobileguard.R;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Setup3Activity extends SetUpBaseActivity {
	private static final String TAG = "Setup3Activity";
	private EditText et_setup3_phone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup3);
		et_setup3_phone = (EditText) findViewById(R.id.et_setup3_phone);
		et_setup3_phone.setText(sharedPreferences.getString(
				MobileGuard.APP_SAFE_NUMBER, null));
	}

	public void selectSafeNumber(View v) {
		Intent intent = new Intent(this, SelectContactActivity.class);
		startActivityForResult(intent, 0);
	}

	@Override
	public void startNextUi() {
		// TODO Auto-generated method stub
		String phone = et_setup3_phone.getText().toString().trim();
		if (TextUtils.isEmpty(phone)) {
			Toast.makeText(this, "安全號碼不能爲空", Toast.LENGTH_SHORT).show();
			return;
		}
		Editor editor = sharedPreferences.edit();
		editor.putString(MobileGuard.APP_SAFE_NUMBER, phone);
		editor.commit();
		startNewActivityAndFinsh(Setup4Activity.class);
		overridePendingTransition(R.anim.anim_next_in, R.anim.anim_next_out);
	}

	@Override
	public void startPreviousUi() {
		// TODO Auto-generated method stub
		startNewActivityAndFinsh(Setup2Activity.class);
		overridePendingTransition(R.anim.anim_previous_in,
				R.anim.anim_previous_out);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		// Log.i(TAG, "====onActivityResult====");
		if (data != null) {
			String phone = data.getStringExtra("phoneNumber");
			Log.i(TAG, "phoneNumber = " + phone);
			et_setup3_phone.setText(phone);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
