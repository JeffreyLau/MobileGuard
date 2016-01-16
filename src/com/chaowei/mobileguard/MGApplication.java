package com.chaowei.mobileguard;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.chaowei.mobileguard.ui.IviewDialog;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

/**
 * 存放共有的数据
 * 
 * @author Jeffrey
 * 
 */
public class MGApplication extends Application {
	private static final String TAG = "MGApplication";

	private CharSequence ItemLabels[] = new String[] { "手機防盜", "騷擾攔截", "軟件管家",
			"進程管理", "流量統計", "手機殺毒", "系統加速", "常用工具" };
	private int ItemIcons[] = new int[] { R.drawable.lostfind, R.drawable.intercept,
			R.drawable.rjgj, R.drawable.jcgl, R.drawable.lltj, R.drawable.sjsd,
			R.drawable.xtjs, R.drawable.cygj };
	private CharSequence[] ItemDescs = new String[] { "遠程定位手機", "全面攔截騷擾",
			"管理您的軟件", "管理正在運行", "流量一目了然", "病毒無處藏身", "系統快如火箭", "常用工具大全" };

	public static class HomeItem {
		public CharSequence getLabel() {
			return label;
		}

		public int getIconId() {
			return iconId;
		}

		public CharSequence getDesc() {
			return desc;
		}

		CharSequence label;
		int iconId;
		CharSequence desc;
	}

	/**
	 * 功能列表
	 */
	private  List<HomeItem> mHomeItemList;

	public List<HomeItem> getHomeItemList() {
		return mHomeItemList;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

		mHomeItemList = new ArrayList<HomeItem>();
		/**
		 * 初始化功能列表
		 */
		for (int i = 0; i < ItemLabels.length; i++) {
			HomeItem homeItem = new HomeItem();
			homeItem.label = ItemLabels[i];
			homeItem.iconId = ItemIcons[i];
			homeItem.desc = ItemDescs[i];
			mHomeItemList.add(homeItem);
		}
		Log.i(TAG, "MGApplication Creat");
	}
}
