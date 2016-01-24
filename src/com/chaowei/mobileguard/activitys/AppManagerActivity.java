
package com.chaowei.mobileguard.activitys;

import java.util.ArrayList;
import java.util.List;

import com.chaowei.mobileguard.R;
import com.chaowei.mobileguard.domain.AppInfo;
import com.chaowei.mobileguard.utils.PackageInfoUtils;
import com.chaowei.mobileguard.utils.SystemInfoUtils;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AppManagerActivity extends Activity {
    private static final String TAG = "AppManagerActivity";
    private TextView tv_external_avail_size;
    private TextView tv_internal_avail_size;
    private TextView tv_default_scroll;
    private ListView lv_all_app;
    private LinearLayout ll_load_layout;
    private List<AppInfo> mAppInfoList;
    private List<AppInfo> mSystemAppInfoList;
    private List<AppInfo> mUserAppInfoList;
    private PopupWindow mPopupWindow;
    private AppUninstallBroadCast mAppUninstallBroadCast;
    private ItemAdapter mItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_manager);
        tv_default_scroll = (TextView) findViewById(R.id.tv_default_scroll);
        tv_default_scroll.setVisibility(View.INVISIBLE);
        tv_external_avail_size = (TextView) findViewById(R.id.tv_external_avail_size);
        tv_internal_avail_size = (TextView) findViewById(R.id.tv_internal_avail_size);
        String exSize = Formatter.formatFileSize(this,
                SystemInfoUtils.getExternalStorageAvailSize());
        String inSize = Formatter.formatFileSize(this,
                SystemInfoUtils.getInternalStorageAvailSize());
        tv_internal_avail_size.setText("內部空間:" + inSize);
        tv_external_avail_size.setText("外部空間:" + exSize);
        lv_all_app = (ListView) findViewById(R.id.lv_all_app);
        ll_load_layout = (LinearLayout) findViewById(R.id.ll_load_layout);
        ll_load_layout.setVisibility(View.VISIBLE);
        new ItemAdapterThread().start();
        lv_all_app.setOnItemClickListener(new ItemOnClickListener());
        lv_all_app.setOnScrollListener(new ItemOnScrollListener());
    }

    @Override
    protected void onStart() {
        mAppUninstallBroadCast = new AppUninstallBroadCast();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        filter.addDataScheme("package");
        registerReceiver(mAppUninstallBroadCast, filter);
        super.onStart();
    }

    private class ItemAdapterThread extends Thread {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            mAppInfoList = PackageInfoUtils.getAllApplicationInfos(AppManagerActivity.this);
            mSystemAppInfoList = new ArrayList<AppInfo>();
            mUserAppInfoList = new ArrayList<AppInfo>();
            for (int i = 0; i < mAppInfoList.size(); i++) {
                AppInfo appinfo = mAppInfoList.get(i);
                if (appinfo.isSystemApp()) {
                    mSystemAppInfoList.add(appinfo);
                } else {
                    mUserAppInfoList.add(appinfo);
                }
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    ll_load_layout.setVisibility(View.INVISIBLE);
                    mItemAdapter = new ItemAdapter();
                    lv_all_app.setAdapter(mItemAdapter);

                }
            });

            super.run();
        }
    }

    private class ItemOnClickListener implements OnItemClickListener, OnClickListener {
        private AppInfo mAppInfo;
        private LinearLayout ll_uninstall;
        private LinearLayout ll_onstart;
        private LinearLayout ll_onshare;
        private LinearLayout ll_showinfo;

        private void elementInit(View popView) {
            ll_uninstall = (LinearLayout) popView.findViewById(R.id.ll_uninstall);
            ll_onstart = (LinearLayout) popView.findViewById(R.id.ll_onstart);
            ll_onshare = (LinearLayout) popView.findViewById(R.id.ll_onshare);
            ll_showinfo = (LinearLayout) popView.findViewById(R.id.ll_showinfo);

            ll_uninstall.setOnClickListener(this);
            ll_onstart.setOnClickListener(this);
            ll_onshare.setOnClickListener(this);
            ll_showinfo.setOnClickListener(this);
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (position == 0 || position == mUserAppInfoList.size() + 1)
                return;
            else if (position <= mUserAppInfoList.size()) {
                int newPosition = position - 1;
                mAppInfo = mUserAppInfoList.get(newPosition);
            } else {// 系统程序
                int newPosition = position - 1 - mUserAppInfoList.size() - 1;
                mAppInfo = mSystemAppInfoList.get(newPosition);
            }
            View popView = View.inflate(AppManagerActivity.this, R.layout.item_app_popup, null);
            if (mPopupWindow != null) {
                mPopupWindow.dismiss();
                mPopupWindow = null;
            }
            elementInit(popView);
            mPopupWindow = new PopupWindow(popView, LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            int location[] = new int[2];
            view.getLocationInWindow(location);// 獲取被點擊的view在窗口上的位置
            // 动画显示,必须设置窗体的背景
            mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mPopupWindow.showAtLocation(parent, Gravity.TOP + Gravity.LEFT, location[0] + 60,
                    location[1]);
            ScaleAnimation sa = new ScaleAnimation(0.3f, 1.0f, 0.3f, 1.0f,
                    ScaleAnimation.RELATIVE_TO_SELF, 0, ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
            sa.setDuration(600);
            popView.startAnimation(sa);
        }

        @Override
        public void onClick(View v) {
            if (mPopupWindow != null) {
                mPopupWindow.dismiss();
                mPopupWindow = null;
            }
            if (mAppInfo == null)
                return;
            switch (v.getId()) {
                case R.id.ll_uninstall:
                    if (!mAppInfo.isSystemApp())
                        PackageInfoUtils.uninstallApplication(AppManagerActivity.this,
                                mAppInfo.getPackageName());
                    break;
                case R.id.ll_onstart:
                    PackageInfoUtils.startApplication(AppManagerActivity.this,
                            mAppInfo.getPackageName());
                    break;
                case R.id.ll_onshare:
                    break;

                case R.id.ll_showinfo:
                    PackageInfoUtils.startInstalledAppDetails(AppManagerActivity.this,
                            mAppInfo.getPackageName());
                    break;
                default:
                    break;
            }
        }

    }

    private class ItemOnScrollListener implements AbsListView.OnScrollListener {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                int totalItemCount) {
            // TODO Auto-generated method stub
            if (mPopupWindow != null) {
                mPopupWindow.dismiss();
                mPopupWindow = null;
            }
            if (mSystemAppInfoList != null && mUserAppInfoList != null) {
                tv_default_scroll.setVisibility(View.VISIBLE);
                if (firstVisibleItem <= mUserAppInfoList.size()) {
                    tv_default_scroll.setText("用户程序:" + mUserAppInfoList.size());
                } else {
                    tv_default_scroll.setText("系统程序:" + mSystemAppInfoList.size());
                }
            }
        }
    }

    private class ItemAdapter extends BaseAdapter {
        private View view;
        private ViewHolder viewHolder;
        private AppInfo mAppInfo;

        private class ViewHolder {
            private ImageView iv_appIcon;
            private TextView tv_appLabel;
            private TextView tv_appInternal;
            private TextView tv_appSize;
            // private Button bt_appUninstall;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return 1 + mUserAppInfoList.size() + 1 + mSystemAppInfoList.size(); // 用于显示系统和用户分类
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

            if (position == 0) {
                TextView textView = new TextView(AppManagerActivity.this);
                textView.setText("用户程序:" + mUserAppInfoList.size());
                textView.setBackgroundColor(Color.GRAY);
                textView.setTextColor(Color.WHITE);
                return textView;
            } else if (position == mUserAppInfoList.size() + 1) {
                // 应该显示系统标签
                TextView textView = new TextView(AppManagerActivity.this);
                textView.setText("系统程序:" + mSystemAppInfoList.size());
                textView.setBackgroundColor(Color.GRAY);
                textView.setTextColor(Color.WHITE);
                return textView;
            } else if (position <= mUserAppInfoList.size()) {
                int newPosition = position - 1;
                mAppInfo = mUserAppInfoList.get(newPosition);
            } else {// 系统程序
                int newPosition = position - 1 - mUserAppInfoList.size() - 1;
                mAppInfo = mSystemAppInfoList.get(newPosition);
            }

            if (convertView != null && convertView instanceof RelativeLayout) {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            } else {
                view = View.inflate(AppManagerActivity.this, R.layout.item_app_info, null);
                viewHolder = new ViewHolder();
                viewHolder.iv_appIcon = (ImageView) view.findViewById(R.id.iv_appIcon);
                viewHolder.tv_appLabel = (TextView) view.findViewById(R.id.tv_appLabel);
                viewHolder.tv_appSize = (TextView) view.findViewById(R.id.tv_appSize);
                viewHolder.tv_appInternal = (TextView) view.findViewById(R.id.tv_appInternal);
                // viewHolder.bt_appUninstall = (Button)
                // view.findViewById(R.id.bt_appUninstall);
                view.setTag(viewHolder);

            }
            viewHolder.iv_appIcon.setImageDrawable(mAppInfo.getAppIcon());
            viewHolder.tv_appInternal.setText(mAppInfo.getAppInternal());
            viewHolder.tv_appLabel.setText(mAppInfo.getAppLabel());
            viewHolder.tv_appSize.setText(
                    Formatter.formatFileSize(getApplication(), mAppInfo.getAppSize()));
            return view;
        }
    }

    private class AppUninstallBroadCast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            String packageName = intent.getDataString().replace("package:", "");
            AppInfo appinfo = null;
            for (int i = 0; i < mUserAppInfoList.size(); i++) {
                appinfo = mUserAppInfoList.get(i);
                if (packageName.equals(appinfo.getPackageName()))
                    break;
            }
            if (appinfo != null) {
                mAppInfoList.remove(appinfo);
                mUserAppInfoList.remove(appinfo);
                if (mItemAdapter != null)
                    mItemAdapter.notifyDataSetChanged();
            }
        }

    }

    @Override
    public void finish() {
        // TODO Auto-generated method stub
        unregisterReceiver(mAppUninstallBroadCast);
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
            mPopupWindow = null;
        }
        super.finish();
    }
}
