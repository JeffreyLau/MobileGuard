package com.chaowei.mobileguard.db.dao;

import java.io.ObjectInputStream.GetField;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class NumberAddressDao {

	private static String DB_PATH = "data/data/com.chaowei.mobileguard/files/address.db";

	public static String findNumberAddress(String number) {
		SQLiteDatabase db = SQLiteDatabase.openDatabase(DB_PATH, null,
				SQLiteDatabase.OPEN_READONLY);
		Cursor cursor;

		// 1[34578] +9位號碼
		String location = "未知來電";
		boolean result = number.matches("^1[34578]\\d{9}$");
		if (result) {
			// 手機號碼
			cursor = db
					.rawQuery(
							"select location from data2 where id = (select outkey from data1 where id = ?)",
							new String[] { number.substring(0, 7) });
			if (cursor.moveToNext()) {
				location = cursor.getString(cursor.getColumnIndex("location"));
				// location = cursor.getString(0);
			}
			cursor.close();
		} else {
			switch (number.length()) {
			case 3:// 110,119,999,120
				location = "報警電話";
				break;
			case 4:// 5556
				location = "模擬器電話";
				break;
			case 5:// 40080
				location = "商業客服電話";
				break;
			case 7:
			case 8:
				location = "本地電話";
				break;
			default:
				if (number.length() >= 10 && number.startsWith("0")) {
					cursor = db
							.rawQuery(
									"select location from data2 where area = ?",
									new String[] { number.substring(1, 3) });
					if (cursor.moveToNext()) {
						String temp = cursor.getString(cursor
								.getColumnIndex("location"));
						location = temp.substring(0, temp.length() - 2);
						cursor.close();
					} else {
						cursor = db
								.rawQuery(
										"select location from data2 where area = ?",
										new String[] { number.substring(1, 4) });
						if (cursor.moveToNext()) {
							String temp = cursor.getString(cursor
									.getColumnIndex("location"));
							location = temp.substring(0, temp.length() - 2);
						}
						cursor.close();
					}
				}
				break;
			}
		}
		db.close();
		return location;
	}
}
