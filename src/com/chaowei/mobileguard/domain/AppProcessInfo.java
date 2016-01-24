package com.chaowei.mobileguard.domain;

public class AppProcessInfo extends AppItemInfo {


    private long memSize;
    /**
     * The pid of this process; 0 if none
     */
    private int pid;

    /**
     * The user id of this process.
     */
    private int uid;
    
    public long getMemSize() {
        return memSize;
    }
    public void setMemSize(long memSize) {
        this.memSize = memSize;
    }

    public int getPid() {
        return pid;
    }
    public void setPid(int pid) {
        this.pid = pid;
    }
    public int getUid() {
        return uid;
    }
    public void setUid(int uid) {
        this.uid = uid;
    }
    @Override
    public String toString() {
        return "AppProcessInfo [memSize=" + memSize + ", pid=" + pid + ", uid=" + uid + "]";
    }


}
