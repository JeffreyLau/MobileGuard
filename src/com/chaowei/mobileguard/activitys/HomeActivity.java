
package com.chaowei.mobileguard.activitys;

import java.util.List;

import com.chaowei.mobileguard.MGApplication;
import com.chaowei.mobileguard.MobileGuard;
import com.chaowei.mobileguard.R;
import com.chaowei.mobileguard.MGApplication.HomeItem;
import com.chaowei.mobileguard.ui.InputPWdDialog;
import com.chaowei.mobileguard.ui.SetUpPWdDialog;
import com.chaowei.mobileguard.utils.PrivateInfoUtils;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity extends Activity {
    private static final String TAG = "HomeActivity";
    public static final int APP_FUNCTION_LOSTFIND = 0;
    public static final int APP_FUNCTION_INTERCEPT = 1;
    public static final int APP_FUNCTION_APP_MANAGER = 2;
    public static final int APP_FUNCTION_COMMON_TOOLS = 7;
    private ImageView iv_log;
    private GridView gv_home_item;
    private MGApplication mGApplication;
    private List<HomeItem> mHomeItemList;
    private MGApplication.HomeItem mHomeItem;
    private SharedPreferences sharedPreferences;
    Intent mxIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mGApplication = (MGApplication) getApplication();
        gv_home_item = (GridView) findViewById(R.id.gv_home_item);
        iv_log = (ImageView) findViewById(R.id.iv_logo);
        startObjectAnimation();
        new Thread() {
            public void run() {

                mHomeItemList = mGApplication.getHomeItemList();
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        gv_home_item.setAdapter(new HomeItemAdapter());
                    }
                });
            };
        }.start();

        gv_home_item.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                // TODO Auto-generated method stub
                switch (position) {
                    case APP_FUNCTION_LOSTFIND:
                        if (!isSetupPwd())
                            new SetUpPWdDialog(HomeActivity.this).show();
                        else {
                            if (userConfiged()) {
                                new InputPWdDialog(HomeActivity.this,
                                        "com.chaowei.mobileguard.activitys.LostFindActivity")
                                        .show();
                            } else {
                                new InputPWdDialog(HomeActivity.this,
                                        "com.chaowei.mobileguard.activitys.Setup1Activity")
                                        .show();
                            }
                        }

                        break;
                    case APP_FUNCTION_INTERCEPT:
                        mxIntent = new Intent(HomeActivity.this,
                                InterceptActivity.class);
                        startActivity(mxIntent);
                        break;
                    case APP_FUNCTION_APP_MANAGER:
                        mxIntent = new Intent(HomeActivity.this,
                                AppManagerActivity.class);
                        startActivity(mxIntent);
                        break;

                    case APP_FUNCTION_COMMON_TOOLS:
                        mxIntent = new Intent(HomeActivity.this,
                                ToolsActivity.class);
                        startActivity(mxIntent);
                        break;

                    default:
                        break;
                }
            }
        });
    }

    private void startObjectAnimation() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(iv_log,
                View.ROTATION_Y, 45, 90, 135, 180, 225, 270, 315, 360);
        objectAnimator.setDuration(2000);
        objectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        objectAnimator.setRepeatMode(ObjectAnimator.REVERSE);
        objectAnimator.start();
    }

    private class HomeItemAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mHomeItemList.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            mHomeItem = mHomeItemList.get(position);
            View view = View
                    .inflate(getApplication(), R.layout.item_home, null);
            ImageView iv_log = (ImageView) view
                    .findViewById(R.id.iv_homeitem_icon);
            TextView tv_label = (TextView) view
                    .findViewById(R.id.tv_homeitem_label);
            TextView tv_desc = (TextView) view
                    .findViewById(R.id.tv_homeitem_desc);
            iv_log.setImageResource(mHomeItem.getIconId());
            tv_label.setText(mHomeItem.getLabel());
            tv_desc.setText(mHomeItem.getDesc());
            return view;
        }
    }

    public void enterApplicationSetUi(View view) {
        Intent intent = new Intent(HomeActivity.this, SettingActivity.class);
        startActivity(intent);
    }

    public boolean isSetupPwd() {
        sharedPreferences = getSharedPreferences(MobileGuard.SHARE_PREFERENCE,
                MODE_PRIVATE);
        String password = sharedPreferences.getString(MobileGuard.APP_PASSWORD,
                "");

        return (!password.equals(""));
    }

    public boolean userConfiged() {
        sharedPreferences = getSharedPreferences(MobileGuard.SHARE_PREFERENCE,
                MODE_PRIVATE);
        boolean configed = sharedPreferences.getBoolean(
                MobileGuard.APP_CONFIGED, false);
        return configed;
    }
}
