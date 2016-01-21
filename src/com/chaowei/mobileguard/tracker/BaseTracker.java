
package com.chaowei.mobileguard.tracker;

import android.os.Handler;

public interface BaseTracker {
    
    public static final int MOTION_EVENT_ROKET_NOMAL = 0x10000;
    public static final int MOTION_EVENT_ROKET_STOP = 0x10001;
    public static final int MOTION_EVENT_ROKET_PREPARE = 0x10002;
    public static final int MOTION_EVENT_ROKET_PULL = 0x10003;
    
    public void registerForRoketNomal(Handler h, int what, Object obj);
    public void unregisterForRoketNomal(Handler h);
    public void notifyRoketNomalRegistrants(AsyncResult ar);

    public void registerForRoketPrepare(Handler h, int what, Object obj);
    public void unregisterForRoketPrepare(Handler h);
    public void notifyRoketPrepareRegistrants(AsyncResult ar);

    public void registerForRoketPull(Handler h, int what, Object obj);
    public void unregisterForRoketPull(Handler h);
    public void notifyRoketPullRegistrants(AsyncResult ar);

    public void registerForRoketStop(Handler h, int what, Object obj);
    public void unregisterForRoketStop(Handler h);
    public void notifyRoketStopRegistrants(AsyncResult ar);
}