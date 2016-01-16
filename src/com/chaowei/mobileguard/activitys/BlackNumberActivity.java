package com.chaowei.mobileguard.activitys;

import com.chaowei.mobileguard.R;
import com.chaowei.mobileguard.db.dao.BlackNumberDao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class BlackNumberActivity extends Activity {

	private static String INTERCEPT_MODE_PHONE = "phone";
	private static String INTERCEPT_MODE_SMS = "sms";
	private static String INTERCEPT_MODE_ALL = "all";
	private EditText et_phone_number;
	private RadioGroup rg_mode;

	private BlackNumberDao mBlackNumberDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_black_number);
		et_phone_number = (EditText) findViewById(R.id.et_phone_number);
		rg_mode = (RadioGroup) findViewById(R.id.rg_mode);
		mBlackNumberDao = new BlackNumberDao(this);
	}

	public void onConfirm(View v) {
		String number = et_phone_number.getText().toString().trim();
		if (TextUtils.isEmpty(number)) {
			Toast.makeText(this, "电话不能为空", Toast.LENGTH_SHORT).show();
			return;
		}

		if (!TextUtils.isEmpty(mBlackNumberDao.findMode(number))) {
			Toast.makeText(this, "黑名单已经存在", Toast.LENGTH_SHORT).show();
			return;
		}

		int id = rg_mode.getCheckedRadioButtonId();
		String mode = "phone";
		switch (id) {
		case R.id.rb_phone:
			mode = INTERCEPT_MODE_PHONE;
			break;
		case R.id.rb_sms:
			mode = INTERCEPT_MODE_SMS;
			break;
		case R.id.rb_all:
			mode = INTERCEPT_MODE_ALL;
			break;
		default:
			break;
		}
		boolean result = mBlackNumberDao.add(number, mode);
		Intent intent = new Intent();
		if (result) {
			Toast.makeText(this, "添加黑名单成功", Toast.LENGTH_SHORT).show();
			intent.putExtra("flag", true);

		} else {
			Toast.makeText(this, "添加黑名单失败", Toast.LENGTH_SHORT).show();
			intent.putExtra("flag", false);
		}
		setResult(0, intent);
		finish();
	}

	public void onCancel(View v) {
		finish();
	}
}
