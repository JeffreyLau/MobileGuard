package com.chaowei.mobileguard.activitys;

import java.util.List;

import com.chaowei.mobileguard.R;
import com.chaowei.mobileguard.db.dao.BlackNumberDao;
import com.chaowei.mobileguard.domain.BlackNumberInfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class InterceptActivity extends Activity {
	protected static final String TAG = "InterceptActivity";
	private ListView lv_intercept;
	private List<BlackNumberInfo> mBlackNumberInfoList;
	private BlackNumberDao mBlackNumberDao;
	private InterCeptItemAdapter mInterCeptItemAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intercept);
		lv_intercept = (ListView) findViewById(R.id.lv_intercept);
		mBlackNumberDao = new BlackNumberDao(this);
		new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				mBlackNumberInfoList = mBlackNumberDao.findAll();
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						mInterCeptItemAdapter = new InterCeptItemAdapter();
						lv_intercept.setAdapter(mInterCeptItemAdapter);
					}
				});
			}
		}.start();
	}

	public class InterCeptItemAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mBlackNumberInfoList.size();
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
			View view;// 优化ListView
			ViewHolder viewHolder;
			if (convertView == null) {// 服用历史缓存View,减少view对象的创建
				view = View.inflate(InterceptActivity.this,
						R.layout.item_black_number, null);
				viewHolder = new ViewHolder();
				viewHolder.tv_black_mode = (TextView) view
						.findViewById(R.id.tv_black_mode);
				viewHolder.tv_black_number = (TextView) view
						.findViewById(R.id.tv_black_number);
				viewHolder.iv_black_delete = (ImageView) view
						.findViewById(R.id.iv_black_delete);
				view.setTag(viewHolder);
			} else {// 复用历史缓存View,减少View对象的创建来达到优化的效果
				view = convertView;
				viewHolder = (ViewHolder) view.getTag();//取出子view根据各不同ＩＤ直接取出无需遍历
			}

			final BlackNumberInfo info = mBlackNumberInfoList.get(position);

			viewHolder.iv_black_delete
					.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							boolean result = mBlackNumberDao.delete(info
									.getNumber());
							if (result) {
								Toast.makeText(InterceptActivity.this,
										"删除数据成功", Toast.LENGTH_SHORT).show();
								mBlackNumberInfoList.remove(info);
								mInterCeptItemAdapter.notifyDataSetChanged();
							} else {
								Toast.makeText(InterceptActivity.this,
										"删除数据失败", Toast.LENGTH_SHORT).show();
							}
						}
					});

			viewHolder.tv_black_number.setText(info.getNumber());
			viewHolder.tv_black_mode.setText(info.getMode());

			return view;
		}

		private class ViewHolder {
			TextView tv_black_mode;
			TextView tv_black_number;
			ImageView iv_black_delete;
		}
	}

	public void addBlackNumber(View v) {
		Intent intent = new Intent(this, BlackNumberActivity.class);
		startActivityForResult(intent, 0);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (data != null) {
			boolean flag = data.getBooleanExtra("flag", false);
			if (flag) {
				mBlackNumberInfoList = mBlackNumberDao.findAll();
				mInterCeptItemAdapter.notifyDataSetChanged();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
