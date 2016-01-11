package com.chaowei.mobileguard.activitys;

import com.chaowei.mobileguard.R;
import com.chaowei.mobileguard.utils.PackageInfoUtil;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SplashActivity extends Activity {

	private TextView tv_app_version;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		tv_app_version = (TextView) findViewById(R.id.tv_app_version);
		String appVersion = PackageInfoUtil.getPackageVersion(getApplication());
		tv_app_version.setText(appVersion);
	}
}
