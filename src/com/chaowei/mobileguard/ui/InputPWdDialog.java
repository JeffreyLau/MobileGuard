package com.chaowei.mobileguard.ui;

import com.chaowei.mobileguard.MobileGuard;
import com.chaowei.mobileguard.R;
import com.chaowei.mobileguard.utils.MD5PWDUtils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InputPWdDialog extends AlertDialog {

	private static final String TAG = "SetUpPwdDialog";
	private Builder mBuilder;
	private View view;
	private Button bt_setup_pwd_confirm;
	private Button bt_setup_pwd_cancel;
	private EditText et_input_pwd;
	private AlertDialog mAlertDialog;
	private Context mContext;
	private String className;
	private SharedPreferences mSharedPreferences;

	public InputPWdDialog(Context context, String object) {
		super(context);
		// TODO Auto-generated constructor stub
		dialogInit(context, object);
	}

	public InputPWdDialog(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
	}

	public InputPWdDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		// TODO Auto-generated constructor stub
	}

	public void show() {
		mAlertDialog = mBuilder.show();
	}

	public void dismiss() {
		if (mAlertDialog != null)
			mAlertDialog.dismiss();
		mAlertDialog = null;
	}

	private void dialogInit(Context context, String clName) {
		mContext = context;
		className = clName;
		mSharedPreferences = mContext.getSharedPreferences(
				MobileGuard.SHARE_PREFERENCE, mContext.MODE_PRIVATE);
		mBuilder = new Builder(context);
		view = View.inflate(context, R.layout.enter_pwd_dialog, null);
		mBuilder.setView(view);
		bt_setup_pwd_confirm = (Button) view
				.findViewById(R.id.bt_setup_pwd_confirm);
		bt_setup_pwd_cancel = (Button) view
				.findViewById(R.id.bt_setup_pwd_cancel);
		et_input_pwd = (EditText) view.findViewById(R.id.et_input_pwd);
		bt_setup_pwd_confirm.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Editor editor = mSharedPreferences.edit();
				// this is MD5 file
				String passwordmd5 = mSharedPreferences.getString(
						MobileGuard.APP_PASSWORD, "");
				String password = et_input_pwd.getText().toString().trim();
				if (TextUtils.isEmpty(password)) {
					Toast.makeText(mContext, "密碼不能爲空", Toast.LENGTH_SHORT)
							.show();
					return;
				} else {
					if (!MD5PWDUtils.encode(password).equals(passwordmd5)) {
						Toast.makeText(mContext, "密碼不正確", Toast.LENGTH_SHORT)
								.show();
						return;
					}
					Toast.makeText(mContext, "密碼正確進入手機防盜界面", Toast.LENGTH_SHORT);
					dismiss();

					Intent intent = new Intent();
					intent.setClassName(mContext, className);
					mContext.startActivity(intent);
				}
			}
		});
		bt_setup_pwd_cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});
	}
	
}
