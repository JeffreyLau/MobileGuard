package com.chaowei.mobileguard.db.dao;

import java.util.ArrayList;
import java.util.List;

import com.chaowei.mobileguard.db.BlackNumberOpenHelper;
import com.chaowei.mobileguard.domain.BlackNumberInfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BlackNumberDao {

	private BlackNumberOpenHelper mBlackNumberOpenHelper;

	public boolean add(String number, String mode) {
		SQLiteDatabase db = mBlackNumberOpenHelper.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("number", number);
		contentValues.put("mode", mode);
		long result = db.insert(mBlackNumberOpenHelper.DATABASE_TABLE, null,
				contentValues);
		db.close();
		if (result != -1)
			return true;
		else
			return false;
	}

	public boolean delete(String number) {
		SQLiteDatabase db = mBlackNumberOpenHelper.getWritableDatabase();
		int result = db.delete(mBlackNumberOpenHelper.DATABASE_TABLE,
				"number=?", new String[] { number });
		db.close();
		return ((result > 0) ? true : false);
	}

	public void updateMode(String number, String newMode) {
		SQLiteDatabase db = mBlackNumberOpenHelper.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("mode", newMode);
		db.update(mBlackNumberOpenHelper.DATABASE_TABLE, contentValues,
				"number=?", new String[] { number });
		db.close();
	}

	public String findMode(String number) {
		String mode = "";
		SQLiteDatabase db = mBlackNumberOpenHelper.getReadableDatabase();
		Cursor cursor = db.query(mBlackNumberOpenHelper.DATABASE_TABLE,
				new String[] { "mode" }, "number=?", new String[] { number },
				null, null, null);
		if (cursor.moveToNext()) {
			mode = cursor.getString(cursor.getColumnIndex("mode"));
		}
		cursor.close();
		db.close();

		return mode;
	}

	public String findId(String number) {
		String id = "";
		SQLiteDatabase db = mBlackNumberOpenHelper.getReadableDatabase();
		Cursor cursor = db.query(mBlackNumberOpenHelper.DATABASE_TABLE,
				new String[] { "_id" }, "number=?", new String[] { number },
				null, null, null);
		if (cursor.moveToNext()) {
			id = cursor.getString(cursor.getColumnIndex("_id"));
		}
		cursor.close();
		db.close();

		return id;
	}

	public List<BlackNumberInfo> findAll() {
		SQLiteDatabase db = mBlackNumberOpenHelper.getReadableDatabase();
		ArrayList<BlackNumberInfo> infos = new ArrayList<BlackNumberInfo>();
		Cursor cursor = db.query(mBlackNumberOpenHelper.DATABASE_TABLE,
				new String[] { "_id", "number", "mode" }, null, null, null,
				null, null);
		while (cursor.moveToNext()) {
			String id = cursor.getString(cursor.getColumnIndex("_id"));
			String number = cursor.getString(cursor.getColumnIndex("number"));
			String mode = cursor.getString(cursor.getColumnIndex("mode"));
			BlackNumberInfo blackNumberInfo = new BlackNumberInfo();
			blackNumberInfo.setId(id);
			blackNumberInfo.setNumber(number);
			blackNumberInfo.setMode(mode);
			infos.add(blackNumberInfo);
		}
		cursor.close();
		db.close();
		return infos;
	}

	public BlackNumberDao(Context context) {
		// TODO Auto-generated constructor stub
		mBlackNumberOpenHelper = new BlackNumberOpenHelper(context);
	}

}
