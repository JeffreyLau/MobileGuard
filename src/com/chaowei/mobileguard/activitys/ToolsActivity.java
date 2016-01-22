
package com.chaowei.mobileguard.activitys;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Currency;

import org.xmlpull.v1.XmlSerializer;
import org.xutils.x;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.common.Callback;

import com.chaowei.mobileguard.MobileGuard;
import com.chaowei.mobileguard.R;
import com.chaowei.mobileguard.remote.RemoteServerInfo;
import com.chaowei.mobileguard.remote.UploadCallback;
import com.chaowei.mobileguard.utils.PrivateInfoUtils;



import android.app.Activity;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ToolsActivity extends Activity {
    private static final String TAG = "ToolsActivity";
    private FrameLayout fl_sms_opp;
    private ProgressBar pb_sms_opp;
    private TextView tv_sms_opp;
    private SharedPreferences mSharedPreferences;
    private Handler mHandler;

    private static final int UPDATE_START_SMS_BACKUP = 0x1000;
    private static final int UPDATE_RUN_SMS_BACKUP = 0x1001;
    private static final int UPDATE_STOP_SMS_BACKUP = 0x1002;
    private static final int UPDATE_START_SMS_RESTORE = 0x1003;
    private static final int UPDATE_RUN_SMS_RESTORE = 0x1004;
    private static final int UPDATE_STOP_SMS_RESTORE = 0x1005;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        mHandler = new UiHandler();
        setContentView(R.layout.activity_common_tools);
        fl_sms_opp = (FrameLayout) findViewById(R.id.fl_sms_opp);
        pb_sms_opp = (ProgressBar) findViewById(R.id.pb_sms_opp);
        tv_sms_opp = (TextView) findViewById(R.id.tv_sms_opp);
        fl_sms_opp.setVisibility(View.INVISIBLE);
        mSharedPreferences = getSharedPreferences(MobileGuard.SHARE_PREFERENCE, MODE_PRIVATE);
    }

    private class UiHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub

            switch (msg.what) {
                case UPDATE_START_SMS_BACKUP:
                    tv_sms_opp.setText("短信備份中,請耐心等待...");
                    fl_sms_opp.setVisibility(View.VISIBLE);
                    pb_sms_opp.setMax((Integer) msg.obj);
                    break;
                case UPDATE_RUN_SMS_BACKUP:
                    pb_sms_opp.setProgress((Integer) msg.obj);

                    break;
                case UPDATE_STOP_SMS_BACKUP:
                    fl_sms_opp.setVisibility(View.INVISIBLE);
                    uploadFiletoServer();
                    break;
                case UPDATE_START_SMS_RESTORE:
                    tv_sms_opp.setText("短信還遠中,請耐心等待...");
                    fl_sms_opp.setVisibility(View.VISIBLE);
                    pb_sms_opp.setMax((Integer) msg.obj);
                    break;
                case UPDATE_RUN_SMS_RESTORE:
                    pb_sms_opp.setProgress((Integer) msg.obj);
                    break;
                case UPDATE_STOP_SMS_RESTORE:
                    fl_sms_opp.setVisibility(View.INVISIBLE);
                    break;

                default:
                    break;
            }
            super.handleMessage(msg);
        }


    }

    private void uploadFiletoServer() {
        RequestParams params =
                new RequestParams(RemoteServerInfo.SERVER_URL_UPLOAD_INFO);
        params.setMultipart(true);
        params.addBodyParameter(
                "uploadFile",
                new File(mSharedPreferences.getString(MobileGuard.APP_SMS_BACKUP_PATH,
                        "/sdcard/smsback.xml")));
        
        params.setCancelFast(true);
        //params.setAutoRename(false);
        UploadCallback callback = new UploadCallback();
        Callback.Cancelable cancelable = x.http().post(params, callback);
        callback.setCancelable(cancelable);
    }   
    
    public void enterNumberQueryActivity(View v) {
        Intent intent = new Intent(this, NumberQueryActivity.class);
        startActivity(intent);
    }

    public void enterSmsBackup(View v) {
        new Thread() {
            public void run() {
                PrivateInfoUtils.smsBackup(ToolsActivity.this,
                        new PrivateInfoUtils.BackupUiCallback() {

                            @Override
                            public void stopSmsBackup() {
                                // TODO Auto-generated method stub
                                Message message = new Message();
                                message.what = UPDATE_STOP_SMS_BACKUP;
                                mHandler.sendMessage(message);
                            }

                            @Override
                            public void startSmsBackup(int max) {
                                // TODO Auto-generated method stub
                                Message message = new Message();
                                message.what = UPDATE_START_SMS_BACKUP;
                                message.obj = max;
                                mHandler.sendMessage(message);
                            }

                            @Override
                            public void runSmsBackup(int pregress) {
                                // TODO Auto-generated method stub
                                Message message = new Message();
                                message.what = UPDATE_RUN_SMS_BACKUP;
                                message.obj = pregress;
                                mHandler.sendMessage(message);
                            }
                        });
            };
        }.start();

    }

    public void enterSmsRestore(View v) {

        new Thread() {
            public void run() {
                PrivateInfoUtils.smsRestore(ToolsActivity.this,
                        new PrivateInfoUtils.RestoreUiCallback() {

                            @Override
                            public void stopSmsRestore() {
                                // TODO Auto-generated method stub
                                Message message = new Message();
                                message.what = UPDATE_STOP_SMS_RESTORE;
                                mHandler.sendMessage(message);
                            }

                            @Override
                            public void startSmsRestore(int max) {
                                // TODO Auto-generated method stub
                                Message message = new Message();
                                message.what = UPDATE_START_SMS_RESTORE;
                                message.obj = max;
                                mHandler.sendMessage(message);
                            }

                            @Override
                            public void runSmsRestore(int pregress) {
                                // TODO Auto-generated method stub
                                Message message = new Message();
                                message.what = UPDATE_RUN_SMS_RESTORE;
                                message.obj = pregress;
                                mHandler.sendMessage(message);
                            }
                        });
            };
        }.start();
    }
}
