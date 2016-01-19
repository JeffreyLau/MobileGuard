package com.chaowei.mobileguard.tracker;

import android.os.Handler;
import android.os.Message;

public abstract class HandlerTracker extends Handler {
	// ***** Overridden from Handler

	@Override
	public abstract void handleMessage(Message msg);

	public abstract void registerForMotionEvent(Handler h, int what, Object obj);

	public abstract void unregisterForMotionEvent(Handler h);
}
