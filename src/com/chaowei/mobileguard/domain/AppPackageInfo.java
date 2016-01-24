
package com.chaowei.mobileguard.domain;


public class AppPackageInfo extends AppItemInfo {

    private long appSize;
    private CharSequence appInternal;

    public long getAppSize() {
        return appSize;
    }

    public void setAppSize(long appSize) {
        this.appSize = appSize;
    }

    public CharSequence getAppInternal() {
        return appInternal;
    }

    public void setAppInternal(CharSequence appInternal) {
        this.appInternal = appInternal;
    }

    @Override
    public String toString() {
        return "AppPackageInfo [appSize=" + appSize + ", appInternal=" + appInternal + "]";
    }

    
}
