
package com.chaowei.mobileguard.utils;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.os.Environment;

public class SystemInfoUtils {

    public static boolean isServiceRuning(Context context, String serviceFullname) {

        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningServiceInfo> infos = am.getRunningServices(100);
        for (RunningServiceInfo runningServiceInfo : infos) {
            if (serviceFullname.equals(runningServiceInfo.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static long getInternalStorageSize() {
        return Environment.getDataDirectory().getTotalSpace();

    }

    public static long getInternalStorageAvailSize() {
        return Environment.getDataDirectory().getFreeSpace();

    }

    public static long getExternalStorageSize() {
        return Environment.getExternalStorageDirectory().getTotalSpace();

    }

    public static long getExternalStorageAvailSize() {
        return Environment.getExternalStorageDirectory().getFreeSpace();

    }
}
