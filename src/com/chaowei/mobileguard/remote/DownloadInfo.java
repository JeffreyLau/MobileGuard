package com.chaowei.mobileguard.remote;

import java.io.File;

public class DownloadInfo {

    public DownloadInfo() {
        // TODO Auto-generated constructor stub
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
    public String getdPath() {
        return dPath;
    }
    public void setdPath(String dPath) {
        this.dPath = dPath;
    }
    public File getFile() {
        return file;
    }
    public void setFile(File file) {
        this.file = file;
    }
    private File file;
    private long total;
    private long current;
    private boolean isDownloading;
    private String dPath;
}
