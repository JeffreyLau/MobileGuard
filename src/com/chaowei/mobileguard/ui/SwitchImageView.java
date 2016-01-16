package com.chaowei.mobileguard.ui;

import com.chaowei.mobileguard.R;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class SwitchImageView extends ImageView {

	private boolean switchStatus = true;

	public boolean getSwitchStatus() {
		return switchStatus;
	}

	public void setSwitchStatus(boolean switchStatus) {
		this.switchStatus = switchStatus;
		if (switchStatus) {
			setImageResource(R.drawable.on);
		} else {
			setImageResource(R.drawable.off);
		}
	}

	public void changeSwitchStatus() {
		setSwitchStatus(!switchStatus);
	}

	public SwitchImageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public SwitchImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public SwitchImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

}
