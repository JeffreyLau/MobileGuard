package com.chaowei.mobileguard.activitys;

import com.chaowei.mobileguard.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CommonToolsActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_common_tools);
	}

	public void enterNumberQueryActivity(View v) {
		Intent intent = new Intent(this, NumberQueryActivity.class);
		startActivity(intent);
	}
}
