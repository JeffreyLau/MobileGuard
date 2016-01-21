
package com.chaowei.mobileguard.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import com.chaowei.mobileguard.MobileGuard;
import com.chaowei.mobileguard.domain.ContactInfo;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.util.Xml;

public class PrivateInfoUtils {

    private static final String TAG = "PrivateInfoUtils";

    public interface BackupUiCallback {

        public void startSmsBackup(int max);

        public void runSmsBackup(int pregress);

        public void stopSmsBackup();
    }

    public interface RestoreUiCallback {

        public void startSmsRestore(int max);

        public void runSmsRestore(int pregress);

        public void stopSmsRestore();
    }

    public static List<ContactInfo> getAllContactInfos(Context context) {
        List<ContactInfo> infos = new ArrayList<ContactInfo>();
        ContentResolver resolver = context.getContentResolver();
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        Uri datauri = Uri.parse("content://com.android.contacts/data");
        Cursor cursor = resolver.query(uri, new String[] {
                "contact_id"
        },
                null, null, null);
        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            // System.out.println("Id:" + id);
            if (id != null) {
                ContactInfo info = new ContactInfo();
                Cursor datacursor = resolver.query(datauri, new String[] {
                        "data1", "mimetype"
                }, "raw_contact_id=?",
                        new String[] {
                            id
                        }, null);
                while (datacursor.moveToNext()) {
                    String data1 = datacursor.getString(0);
                    String mimetype = datacursor.getString(1);
                    if ("vnd.android.cursor.item/name".equals(mimetype)) {
                        info.setName(data1);
                    } else if ("vnd.android.cursor.item/im".equals(mimetype)) {
                        info.setQq(data1);
                    } else if ("vnd.android.cursor.item/email_v2"
                            .equals(mimetype)) {
                        info.setEmail(data1);
                    } else if ("vnd.android.cursor.item/phone_v2"
                            .equals(mimetype)) {
                        info.setPhone(data1);
                    }
                }
                datacursor.close();
                infos.add(info);
            }
        }
        cursor.close();
        return infos;
    }

    /**
     * <xml头> <smsinfos> 根节点代表所有短信 <smsinfo>　每一条短信用此对象来描述 <address>110</address>
     * <body>helloworld</body> <type> </type> <date></date> </smsinfo>
     * </smsinfos>根节点代表所有短信
     * 
     * @param v
     */

    public static void smsBackup(Context context, BackupUiCallback callback) {
        try {
            File file;
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                file = new File(Environment.getExternalStorageDirectory(), "smsback.xml");
            } else {
                file = new File(context.getFilesDir(), "smsback.xml");

            }
            // Log.i(TAG,"文件的path = " + file.getPath());
            ContentResolver resolver = context.getContentResolver();
            Uri uri = Uri.parse("content://sms/");
            FileOutputStream fos = new FileOutputStream(file);
            XmlSerializer xmlSerializer = Xml.newSerializer();
            xmlSerializer.setOutput(fos, "utf-8");
            xmlSerializer.startDocument("utf-8", true);
            xmlSerializer.startTag(null, "smsinfos");
            Cursor cursor = resolver.query(uri, new String[] {
                    "_id","address", "body", "type", "date"
            }, null, null, null);
            callback.startSmsBackup(cursor.getColumnCount());
            int pregress = 0;

            while (cursor.moveToNext()) {

                xmlSerializer.startTag(null, "smsinfo");
                
                String _id = cursor.getString(cursor.getColumnIndex("_id"));
                String address = cursor.getString(cursor.getColumnIndex("address"));
                String body = cursor.getString(cursor.getColumnIndex("body"));
                String type = cursor.getString(cursor.getColumnIndex("type"));
                String date = cursor.getString(cursor.getColumnIndex("date"));

                xmlSerializer.startTag(null, "_id");
                xmlSerializer.text(_id);
                xmlSerializer.endTag(null, "_id");

                xmlSerializer.startTag(null, "address");
                xmlSerializer.text(address);
                xmlSerializer.endTag(null, "address");

                xmlSerializer.startTag(null, "body");
                xmlSerializer.text(body);
                xmlSerializer.endTag(null, "body");

                xmlSerializer.startTag(null, "type");
                xmlSerializer.text(type);
                xmlSerializer.endTag(null, "type");

                xmlSerializer.startTag(null, "date");
                xmlSerializer.text(date);
                xmlSerializer.endTag(null, "date");

                xmlSerializer.endTag(null, "smsinfo");

                pregress++;
                callback.runSmsBackup(pregress);
            }
            cursor.close();
            xmlSerializer.endTag(null, "smsinfos");
            xmlSerializer.endDocument();
            fos.close();
            SharedPreferences sharedPreferences = context.getSharedPreferences(
                    MobileGuard.SHARE_PREFERENCE, context.MODE_PRIVATE);
            Editor editor = sharedPreferences.edit();
            editor.putString(MobileGuard.APP_SMS_BACKUP_PATH, file.getPath());
            editor.commit();
            callback.stopSmsBackup();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void smsRestore(Context context, RestoreUiCallback callback) {
        try {

            SharedPreferences sharedPreferences = context.getSharedPreferences(
                    MobileGuard.SHARE_PREFERENCE, context.MODE_PRIVATE);
            String filePath = sharedPreferences.getString(MobileGuard.APP_SMS_BACKUP_PATH,
                    "/sdcard/smsback.xml");
            XmlPullParser xmlPullParser = Xml.newPullParser();
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            xmlPullParser.setInput(fis, "utf-8");
            int eventType = xmlPullParser.getEventType();// START_DOCUMENT
                                                         // default return
            ContentResolver resolver = context.getContentResolver();
            Uri uri = Uri.parse("content://sms/");
            ContentValues values = new ContentValues();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:// 起始节点
                        // 信息节点的时候新建一个contentValues
                        if ("smsinfo".equals(xmlPullParser.getName())) {
                            values.clear();//清除其他數據
                        } else if ("_id".equals(xmlPullParser.getName())) {
                            values.put("_id", xmlPullParser.nextText());
                        } else if ("address".equals(xmlPullParser.getName())) {
                            values.put("address", xmlPullParser.nextText());
                        } else if ("body".equals(xmlPullParser.getName())) {
                            values.put("body", xmlPullParser.nextText());
                        } else if ("type".equals(xmlPullParser.getName())) {
                            values.put("type", xmlPullParser.nextText());
                        } else if ("date".equals(xmlPullParser.getName())) {
                            values.put("date", xmlPullParser.nextText());
                        }
                        break;

                    case XmlPullParser.END_TAG: {
                        if ("smsinfo".equals(xmlPullParser.getName())) {// 如果节点是smsinfo
                            Uri uril = resolver.insert(uri, values);// 先数据库中插入数据
                            if (uril != null) {
                                Log.i(TAG, "插入短信成功");
                            } else {
                                Log.i(TAG, "插入短信失败");
                            }
                            values.clear();
                        }
                    }
                    default:
                        break;
                }
                eventType = xmlPullParser.next();// 继续往下解析
            }
            fis.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
