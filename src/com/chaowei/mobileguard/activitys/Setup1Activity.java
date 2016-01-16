package com.chaowei.mobileguard.activitys;

import com.chaowei.mobileguard.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class Setup1Activity extends SetUpBaseActivity {

	private static final String TAG = "Setup1Activity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup1);

	}

	@Override
	public void startNextUi() {
		// TODO Auto-generated method stub
		startNewActivityAndFinsh(Setup2Activity.class);
		overridePendingTransition(R.anim.anim_next_in, R.anim.anim_next_out);
	}

	@Override
	public void startPreviousUi() {
		// TODO Auto-generated method stub
		
	}




}
