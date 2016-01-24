
package com.chaowei.mobileguard.domain;

import android.content.pm.ApplicationInfo;
import android.graphics.drawable.Drawable;

public class AppInfo {

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public CharSequence getAppLabel() {
        return appLabel;
    }

    public void setAppLabel(CharSequence appLabel) {
        this.appLabel = appLabel;
    }

    public CharSequence getPackageName() {
        return packageName;
    }

    public void setPackageName(CharSequence packageName) {
        this.packageName = packageName;
    }

    public long getAppSize() {
        return appSize;
    }

    public void setAppSize(long appSize) {
        this.appSize = appSize;
    }

    private Drawable appIcon;
    private CharSequence appLabel;
    private CharSequence packageName;
    private long appSize;
    private int flag;
    private CharSequence appInternal;

    public CharSequence getAppInternal() {
        return appInternal;
    }

    public void setAppInternal(CharSequence appInternal) {
        this.appInternal = appInternal;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public boolean isSystemApp() {
        return (getFlag() & ApplicationInfo.FLAG_SYSTEM) != 0;
    }

    public boolean isInternal() {
        return (getFlag() & ApplicationInfo.FLAG_EXTERNAL_STORAGE) == 0;
    }

    @Override
    public String toString() {
        return "AppInfo [appIcon=" + appIcon + ", appLabel=" + appLabel + ", packageName="
                + packageName + ", appSize=" + appSize + ", flag=" + flag + "]";
    }

}
