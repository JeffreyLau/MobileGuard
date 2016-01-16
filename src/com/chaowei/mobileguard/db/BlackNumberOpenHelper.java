package com.chaowei.mobileguard.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class BlackNumberOpenHelper extends SQLiteOpenHelper {

	public static String DATABASE_NAME = "mobileguard.db";
	public static String DATABASE_TABLE = "blacknumber";

	public BlackNumberOpenHelper(Context context) {
		// super(context, name, factory, version);
		super(context, DATABASE_NAME, null, 1);
	}

	private void dbCreat(String tablename, SQLiteDatabase db) {
		db.execSQL("create table "
				+ tablename
				+ " (_id integer primary key autoincrement,number vchar(20),name vchar(20),mode vchar(10))");
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		dbCreat(DATABASE_TABLE, db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
