package com.chaowei.mobileguard.activitys;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.chaowei.mobileguard.R;
import com.chaowei.mobileguard.db.dao.NumberAddressDao;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NumberQueryActivity extends Activity {
	private static final String TAG = "NumberQueryActivity";
	private EditText et_number;
	private TextView tv_location;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_number_query);
		et_number = (EditText) findViewById(R.id.et_number);
		tv_location = (TextView) findViewById(R.id.tv_location);
		copyAddressDbFromAssert();// 將該函數放在splash界面去拷貝

		et_number.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				if (s.length() >= 10) {
					String location = NumberAddressDao.findNumberAddress(s
							.toString().trim());
					// Log.i(TAG, "location = " + location);
					tv_location.setText("歸屬地爲:" + location);
				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

	}

	public void query(View v) {
		String mobilenumber = et_number.getText().toString().trim();
		if (TextUtils.isEmpty(mobilenumber)) {
			Toast.makeText(this, "電話號碼不能爲空", Toast.LENGTH_SHORT).show();
			Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
			et_number.startAnimation(shake);
			return;
		}

		String location = NumberAddressDao.findNumberAddress(mobilenumber);
		// Log.i(TAG, "location = " + location);
		tv_location.setText("歸屬地爲:" + location);
	}

	private void copyAddressDbFromAssert() {// 將該函數放在splash界面去拷貝
		final File file = new File(getFilesDir(), "address.db");
		if (file.exists() && file.length() > 0) {
			Log.i(TAG, "文件以及存在");
			return;
		} else {
			new Thread() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						InputStream is = getAssets().open("address.db");
						FileOutputStream fos = new FileOutputStream(file);
						byte[] buffer = new byte[1024];
						int len = -1;
						while ((len = is.read(buffer)) != -1) {
							fos.write(buffer, 0, len);
						}
						fos.close();
						is.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					super.run();
				}
			}.start();

		}

	}
}
