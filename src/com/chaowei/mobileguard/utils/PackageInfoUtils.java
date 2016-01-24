
package com.chaowei.mobileguard.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.chaowei.mobileguard.domain.AppInfo;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources.NotFoundException;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import android.util.Log;

public class PackageInfoUtils {

    private static final String TAG = "PackageInfoUtils";

    public static String getPackageVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return info.versionName;
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "version error";
    }

    public static InputStream getInputStreamFromString(String string) {
        if ((string != null) && !string.trim().equals("")) {
            ByteArrayInputStream bis = new ByteArrayInputStream(
                    string.getBytes());
            return bis;
        }
        return null;
    }

    public static InputStream getInputStreamFromUrl(int urlId, Context context)
            throws ProtocolException, MalformedURLException, NotFoundException,
            IOException {
        // TODO Auto-generated method stub
        String urlAddress = context.getString(urlId);
        URL url = new URL(urlAddress);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url
                .openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setReadTimeout(2000);
        int returnCode = httpURLConnection.getResponseCode();
        httpURLConnection.getInputStream();
        if (returnCode == 200) {
            Log.i(TAG, "连接服务器成功");
            return httpURLConnection.getInputStream();
        } else if (returnCode == 200) {
            Log.i(TAG, "404 ERROR");
        } else {
            Log.i(TAG, "NOt Found ERROR");
        }
        return null;
    }

    //
    public static String setInputStreamToString(InputStream is)
            throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;// read data of InputStream to buffer;
        while ((len = is.read(buffer)) != -1) {
            baos.write(buffer, 0, len);// write data of buffer to baos
        }
        return baos.toString();

    }

    public static List<AppInfo> getAllApplicationInfos(Context context) {
        PackageManager mPackageManager = context.getPackageManager();
        List<PackageInfo> packages = mPackageManager.getInstalledPackages(0);
        ArrayList<AppInfo> AppInfoList = new ArrayList<AppInfo>();
        int installedListSize = packages.size();
        for (int i = 0; i < installedListSize; i++) {
            PackageInfo mPackageInfo = packages.get(i);
            ApplicationInfo mApplicationInfo = mPackageInfo.applicationInfo;
            CharSequence packageName = mPackageInfo.packageName;
            Drawable appIcon = mApplicationInfo.loadIcon(mPackageManager);
            CharSequence appLabel = mApplicationInfo.loadLabel(mPackageManager);
            File file = new File(mApplicationInfo.sourceDir);
            long appSize = file.length();
            int flags = mApplicationInfo.flags;
            AppInfo mAppInfo = new AppInfo();
            mAppInfo.setAppIcon(appIcon);
            mAppInfo.setAppLabel(appLabel);
            mAppInfo.setPackageName(packageName);
            mAppInfo.setAppSize(appSize);
            mAppInfo.setFlag(flags);
            if (mAppInfo.isInternal()) {
                mAppInfo.setAppInternal("internal");
            } else {
                mAppInfo.setAppInternal("external");
            }
            AppInfoList.add(mAppInfo);
            // Log.i(TAG, "AppInfo = " + mAppInfo);
        }
        return AppInfoList;
    }

    public static void installApplication(Context context, File file) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    public static void uninstallApplication(Context context, CharSequence packageName) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.DELETE");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setData(Uri.parse("package:" + packageName));
        context.startActivity(intent);
    }

    public static void startApplication(Context context, CharSequence packageName) {
        PackageManager mPackageManager = context.getPackageManager();
        Intent intent = mPackageManager.getLaunchIntentForPackage((String) packageName);
        if (intent != null)
            context.startActivity(intent);
    }

    public static void startInstalledAppDetails(Context context, CharSequence packageName) {
        Intent intent = new Intent();
        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setData(Uri.parse("package:" + packageName));
        context.startActivity(intent);
    }
}
