package com.chaowei.mobileguard.activitys;

import java.util.List;

import com.chaowei.mobileguard.R;
import com.chaowei.mobileguard.domain.ContactInfo;
import com.chaowei.mobileguard.utils.ContactInfoUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class SelectContactActivity extends Activity {

	protected static final String TAG = "SelectContactActivity";
	private ListView mListView;
	private List<ContactInfo> mListContactInfo;
	private LinearLayout ll_load_layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_contact);
		ll_load_layout = (LinearLayout) findViewById(R.id.ll_load_layout);
		mListView = (ListView) findViewById(R.id.lv_select_contact);
		ll_load_layout.setVisibility(View.VISIBLE);
		new Thread() {
			public void run() {

				mListContactInfo = ContactInfoUtils
						.getAllContactInfos(SelectContactActivity.this);
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						ll_load_layout.setVisibility(View.INVISIBLE);
						mListView.setAdapter(new ContactListViewAdapter());
					}
				});
			};
		}.start();

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				String phone = mListContactInfo.get(position).getPhone();
				// Log.i(TAG, "phone = " + phone);
				Intent intent = new Intent();
				intent.putExtra("phoneNumber", phone);
				setResult(0, intent);
				finish();
			}

		});
	}

	private class ContactListViewAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mListContactInfo.size();
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
			View view;
			if (convertView == null) {//防止内存溢出
				view = View.inflate(SelectContactActivity.this,
						R.layout.item_contact, null);
			} else {
				view = convertView;
			}
			TextView tv_select_name = (TextView) view
					.findViewById(R.id.tv_select_name);
			TextView tv_select_number = (TextView) view
					.findViewById(R.id.tv_select_number);
			tv_select_name.setText(mListContactInfo.get(position).getName());
			tv_select_number.setText(mListContactInfo.get(position).getPhone());
			return view;
		}

	}
}
