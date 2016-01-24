
package com.chaowei.mobileguard.activitys;

import java.util.ArrayList;
import java.util.List;

import com.chaowei.mobileguard.R;
import com.chaowei.mobileguard.domain.AppProcessInfo;
import com.chaowei.mobileguard.utils.PackageInfoUtils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ProcessManagerActivity extends Activity {

    private TextView tv_process_cnt;
    private TextView tv_memory_status;
    private ListView lv_all_process;
    private LinearLayout ll_load_layout;

    private List<AppProcessInfo> mProcessInfoList;
    private List<AppProcessInfo> mSystemProcessInfoList;
    private List<AppProcessInfo> mUserProcessInfoList;
    private ItemAdapter mItemAdapter;
    private ItemAdapter.ViewHolder mViewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_manager);
        tv_process_cnt = (TextView) findViewById(R.id.tv_process_cnt);
        tv_memory_status = (TextView) findViewById(R.id.tv_memory_status);
        tv_process_cnt.setText("运行中进程:" + PackageInfoUtils.getRunningProcessesCount(this));
        tv_memory_status.setText("可用内存:"
                + Formatter.formatFileSize(this, PackageInfoUtils.getAvailableMemory(this)));
        lv_all_process = (ListView) findViewById(R.id.lv_all_process);
        ll_load_layout = (LinearLayout) findViewById(R.id.ll_load_layout);
        ll_load_layout.setVisibility(View.VISIBLE);
        new ItemAdapterThread().start();
        lv_all_process.setOnItemClickListener(new ItemOnClickListener());
    }

    private class ItemAdapterThread extends Thread {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            mProcessInfoList = PackageInfoUtils
                    .getRunningAppProcessInfos(ProcessManagerActivity.this);
            mSystemProcessInfoList = new ArrayList<AppProcessInfo>();
            mUserProcessInfoList = new ArrayList<AppProcessInfo>();
            for (int i = 0; i < mProcessInfoList.size(); i++) {
                AppProcessInfo processinfo = mProcessInfoList.get(i);
                if (processinfo.isSystemApp()) {
                    mSystemProcessInfoList.add(processinfo);
                } else {
                    mUserProcessInfoList.add(processinfo);
                }
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    ll_load_layout.setVisibility(View.INVISIBLE);
                    mItemAdapter = new ItemAdapter();
                    lv_all_process.setAdapter(mItemAdapter);

                }
            });

            super.run();
        }
    }

    private class ItemAdapter extends BaseAdapter {
        private View view;
        private ViewHolder viewHolder;
        private AppProcessInfo mProcessinfo;

        private class ViewHolder {
            private ImageView iv_processIcon;
            private TextView tv_processLabel;
            private TextView tv_processMemsize;
            private CheckBox cb_processClean;
            // private Button bt_appUninstall;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return 1 + mUserProcessInfoList.size() + 1 + mSystemProcessInfoList.size(); // 用于显示系统和用户分类
        }

        // 返回当前位置得到的对象
        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            if (position == 0) {
                return null;
            } else if (position == mUserProcessInfoList.size() + 1) {
                // 应该显示系统标签
                return null;
            } else if (position <= mUserProcessInfoList.size()) {
                int newPosition = position - 1;
                mProcessinfo = mUserProcessInfoList.get(newPosition);
            } else {// 系统程序
                int newPosition = position - 1 - mUserProcessInfoList.size() - 1;
                mProcessinfo = mSystemProcessInfoList.get(newPosition);
            }
            return mProcessinfo;
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
                TextView textView = new TextView(ProcessManagerActivity.this);
                textView.setText("用户进程:" + mUserProcessInfoList.size());
                textView.setBackgroundColor(Color.GRAY);
                textView.setTextColor(Color.WHITE);
                return textView;
            } else if (position == mUserProcessInfoList.size() + 1) {
                // 应该显示系统标签
                TextView textView = new TextView(ProcessManagerActivity.this);
                textView.setText("系统进程:" + mSystemProcessInfoList.size());
                textView.setBackgroundColor(Color.GRAY);
                textView.setTextColor(Color.WHITE);
                return textView;
            } else if (position <= mUserProcessInfoList.size()) {
                int newPosition = position - 1;
                mProcessinfo = mUserProcessInfoList.get(newPosition);
            } else {// 系统程序
                int newPosition = position - 1 - mUserProcessInfoList.size() - 1;
                mProcessinfo = mSystemProcessInfoList.get(newPosition);
            }
            if (convertView != null && convertView instanceof RelativeLayout) {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            } else {
                view = View.inflate(ProcessManagerActivity.this, R.layout.item_process_info, null);
                viewHolder = new ViewHolder();
                viewHolder.iv_processIcon = (ImageView) view.findViewById(R.id.iv_processIcon);
                viewHolder.tv_processLabel = (TextView) view.findViewById(R.id.tv_processLabel);
                viewHolder.tv_processMemsize = (TextView) view.findViewById(R.id.tv_processMemsize);
                viewHolder.cb_processClean = (CheckBox) view.findViewById(R.id.cb_processClean);
                // viewHolder.bt_appUninstall = (Button)
                // view.findViewById(R.id.bt_appUninstall);
                view.setTag(viewHolder);
            }
            viewHolder.iv_processIcon.setImageDrawable(mProcessinfo.getAppIcon());
            viewHolder.tv_processLabel.setText(mProcessinfo.getAppLabel());
            viewHolder.tv_processMemsize.setText(
                    Formatter.formatFileSize(getApplication(), mProcessinfo.getMemSize()));
            mViewHolder = viewHolder;
            return view;
        }
    }

    private class ItemOnClickListener implements OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // TODO Auto-generated method stub
            Object obj = lv_all_process.getItemAtPosition(position);
            if (obj != null) {
                if (mViewHolder != null)
                    mViewHolder.cb_processClean.
                            setChecked(!mViewHolder.cb_processClean.isChecked());
            }
        }

    }

}
