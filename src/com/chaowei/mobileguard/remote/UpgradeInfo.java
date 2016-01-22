
package com.chaowei.mobileguard.remote;

public class UpgradeInfo {

    private static UpgradeInfo sInstance;

    // http://192.168.0.127:8080/mobileguard_updateinfo.json
    public static final String SERVER_URL_UPDATE_INFO = "http://192.168.1.100:8080/updateinfo.json";

    public static final UpgradeInfo getDefault() {
        if (sInstance == null) {
            sInstance = new UpgradeInfo();
        }
        return sInstance;
    }

    public String getlVersion() {
        return lVersion;
    }

    public void setlVersion(String lVersion) {
        this.lVersion = lVersion;
    }

    public String getsVersion() {
        return sVersion;
    }

    public void setsVersion(String sVersion) {
        this.sVersion = sVersion;
    }

    public String getdPath() {
        return dPath;
    }

    public void setdPath(String dPath) {
        this.dPath = dPath;
    }

    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getCurrent() {
        return current;
    }

    public void setCurrent(long current) {
        this.current = current;
    }

    public boolean isDownloading() {
        return isDownloading;
    }

    public void setDownloading(boolean isDownloading) {
        this.isDownloading = isDownloading;
    }

    private long total;
    private long current;
    private boolean isDownloading;
    private String dPath;
    private String lVersion;
    private String sVersion;


}
