package com.chaowei.mobileguard.ui;

import com.chaowei.mobileguard.MobileGuard;
import com.chaowei.mobileguard.R;
import com.chaowei.mobileguard.utils.MD5PWDUtils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SetUpPWdDialog extends AlertDialog {

	private static final String TAG = "SetUpPwdDialog";
	private Builder mBuilder;
	private View view;
	private Button bt_setup_pwd_confirm;
	private Button bt_setup_pwd_cancel;
	private EditText et_input_pwd;
	private EditText et_input_pwd_again;
	private AlertDialog mAlertDialog;
	private Context mContext;
	private SharedPreferences mSharedPreferences;

	public SetUpPWdDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub

		dialogInit(context);
	}

	public SetUpPWdDialog(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
	}

	public SetUpPWdDialog(Context context, boolean cancelable,
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

	private void dialogInit(Context context) {
		mContext = context;
		mSharedPreferences = mContext.getSharedPreferences(
				MobileGuard.SHARE_PREFERENCE, mContext.MODE_PRIVATE);
		mBuilder = new Builder(context);
		view = View.inflate(context, R.layout.setup_pwd_dialog, null);
		mBuilder.setView(view);
		bt_setup_pwd_confirm = (Button) view
				.findViewById(R.id.bt_setup_pwd_confirm);
		bt_setup_pwd_cancel = (Button) view
				.findViewById(R.id.bt_setup_pwd_cancel);
		et_input_pwd = (EditText) view.findViewById(R.id.et_input_pwd);
		et_input_pwd_again = (EditText) view
				.findViewById(R.id.et_input_pwd_again);
		bt_setup_pwd_confirm.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String password = et_input_pwd.getText().toString().trim();
				String password_confirm = et_input_pwd_again.getText()
						.toString().trim();
				if (TextUtils.isEmpty(password)
						|| TextUtils.isEmpty(password_confirm)) {
					Toast.makeText(mContext, "密碼不能爲空", Toast.LENGTH_SHORT)
							.show();
					return;
				} else {
					if (!password.equals(password_confirm)) {
						Toast.makeText(mContext, "兩次密碼輸入不相同",
								Toast.LENGTH_SHORT).show();
						return;
					}
					Editor editor = mSharedPreferences.edit();
					editor.putString(MobileGuard.APP_PASSWORD,
							MD5PWDUtils.encode(password));
					editor.commit();
					dismiss();
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
