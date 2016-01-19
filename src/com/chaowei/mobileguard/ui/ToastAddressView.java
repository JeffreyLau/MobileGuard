package com.chaowei.mobileguard.ui;

import com.chaowei.mobileguard.R;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ToastAddressView {

	private View view;
	private Context mContext;
	private CharSequence mText;
	private WindowManager wm;
	private WindowManager.LayoutParams params;

	public ToastAddressView(Context context) {
		// TODO Auto-generated constructor stub
		this.mContext = context;
	}

	private void windInit(Context context) {
		wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		params = new LayoutParams();
		params.height = WindowManager.LayoutParams.WRAP_CONTENT;
		params.width = WindowManager.LayoutParams.WRAP_CONTENT;
		params.format = PixelFormat.TRANSLUCENT;
		params.type = WindowManager.LayoutParams.TYPE_TOAST;
		params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
				| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
				| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
	}

	public ToastAddressView(Context context, CharSequence text) {
		// TODO Auto-generated constructor stub
		view = View.inflate(context, R.layout.toast_location, null);
		TextView tv_toast_location = (TextView) view
				.findViewById(R.id.tv_toast_location);
		ImageView iv_toast_location = (ImageView) view
				.findViewById(R.id.iv_toast_location);
		tv_toast_location.setText(text);
		wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		params = new LayoutParams();
		params.height = WindowManager.LayoutParams.WRAP_CONTENT;
		params.width = WindowManager.LayoutParams.WRAP_CONTENT;
		params.format = PixelFormat.TRANSLUCENT;
		params.type = WindowManager.LayoutParams.TYPE_TOAST;
		params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
				| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
				| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
	}

	public void show() {
		wm.addView(view, params);
	}

	public void dismiss() {
		wm.removeView(view);
	}
}
