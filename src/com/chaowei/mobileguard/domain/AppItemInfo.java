
package com.chaowei.mobileguard.domain;

import android.content.pm.ApplicationInfo;
import android.graphics.drawable.Drawable;

public class AppItemInfo {

    private Drawable appIcon;
    private CharSequence appLabel;
    private CharSequence packageName;
    private int flag;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

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

    public boolean isSystemApp() {
        return (getFlag() & ApplicationInfo.FLAG_SYSTEM) != 0;
    }

    public boolean isInternal() {
        return (getFlag() & ApplicationInfo.FLAG_EXTERNAL_STORAGE) == 0;
    }

    @Override
    public String toString() {
        return "AppItemInfo [appIcon=" + appIcon + ", appLabel=" + appLabel + ", packageName="
                + packageName + ", flag=" + flag + "]";
    }

}
