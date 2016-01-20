
package com.chaowei.mobileguard.activitys;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.chaowei.mobileguard.MGApplication;
import com.chaowei.mobileguard.MobileGuard;
import com.chaowei.mobileguard.R;
import com.chaowei.mobileguard.db.dao.BlackNumberDao;
import com.chaowei.mobileguard.tracker.BaseTracker;
import com.chaowei.mobileguard.tracker.HandlerTracker;

import android.app.Activity;
import android.app.PendingIntent.OnFinished;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.PhoneStateListener;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

public class SmokeActivity extends Activity {
    private static final String TAG = "SmokeActivity";
    private ImageView iv_whirlpool;
    private ImageView iv_whirlpoolStat2;
    private ImageView iv_smoke_down;
    private ImageView iv_smoke_up;
    private AnimationDrawable whirlAnimation;
    private WindowManager wm;
    private Point screenSize = new Point();
    private BaseTracker mBaseTracker;
    private MotionTracker mMotionTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whirlpool);
        mMotionTracker = new MotionTracker();
        mBaseTracker = HandlerTracker.getTracker();
        mBaseTracker.registerForRoketNomal(mMotionTracker,
                BaseTracker.MOTION_EVENT_ROKET_NOMAL, null);
        mBaseTracker.registerForRoketPrepare(mMotionTracker,
                BaseTracker.MOTION_EVENT_ROKET_PREPARE, null);
        mBaseTracker.registerForRoketPull(mMotionTracker,
                BaseTracker.MOTION_EVENT_ROKET_PULL, null);
        mBaseTracker.registerForRoketStop(mMotionTracker,
                BaseTracker.MOTION_EVENT_ROKET_STOP, null);
        wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        wm.getDefaultDisplay().getSize(screenSize);
        iv_whirlpool = (ImageView) findViewById(R.id.iv_whirlpool);
        iv_whirlpoolStat2 = (ImageView) findViewById(R.id.iv_whirlpoolStat2);
        iv_smoke_down = (ImageView) findViewById(R.id.iv_smoke_down);
        iv_smoke_up = (ImageView) findViewById(R.id.iv_smoke_up);
        whirlAnimation = (AnimationDrawable) iv_whirlpool.getBackground();
        whirlAnimation.start();
    }

    @Override
    protected void onStart() {

        super.onStart();
    }

    private class MotionTracker extends Handler {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case BaseTracker.MOTION_EVENT_ROKET_NOMAL:
                    iv_whirlpool.setVisibility(View.VISIBLE);
                    iv_whirlpoolStat2.setVisibility(View.INVISIBLE);
                    whirlAnimation.start();
                    break;
                case BaseTracker.MOTION_EVENT_ROKET_PREPARE:
                    whirlAnimation.stop();
                    iv_whirlpool.setVisibility(View.INVISIBLE);
                    iv_whirlpoolStat2.setVisibility(View.VISIBLE);
                    break;

                case BaseTracker.MOTION_EVENT_ROKET_PULL:
                    iv_whirlpool.setVisibility(View.INVISIBLE);
                    iv_whirlpoolStat2.setVisibility(View.INVISIBLE);
                    iv_smoke_down.setVisibility(View.VISIBLE);
                    iv_smoke_up.setVisibility(View.VISIBLE);
                    break;
                    
                case BaseTracker.MOTION_EVENT_ROKET_STOP:
                    iv_whirlpool.setVisibility(View.INVISIBLE);
                    iv_whirlpoolStat2.setVisibility(View.INVISIBLE);
                    iv_smoke_down.setVisibility(View.INVISIBLE);
                    iv_smoke_up.setVisibility(View.INVISIBLE);
                    finish();
                    break;

                default:
                    break;
            }
            super.handleMessage(msg);
        }
    }

    @Override
    public void finish() {
        mBaseTracker.unregisterForRoketNomal(mMotionTracker);
        mBaseTracker.unregisterForRoketPrepare(mMotionTracker);
        mBaseTracker.unregisterForRoketPull(mMotionTracker);
        mBaseTracker.unregisterForRoketStop(mMotionTracker);
        super.finish();
    }
}
