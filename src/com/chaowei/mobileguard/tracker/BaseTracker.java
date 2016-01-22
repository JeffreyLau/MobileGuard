
package com.chaowei.mobileguard.tracker;

import android.os.Handler;

public interface BaseTracker {
    
    public static final int MOTION_EVENT_ROKET_NOMAL = 0x10000;
    public static final int MOTION_EVENT_ROKET_STOP = 0x10001;
    public static final int MOTION_EVENT_ROKET_PREPARE = 0x10002;
    public static final int MOTION_EVENT_ROKET_PULL = 0x10003;
    
    public static final int SERVER_REQUEST_SUCCESS = 0x10004;
    public static final int SERVER_REQUEST_FAILURE = 0x10005;
    
    //public static final int UPGRADE_VERSION_SUCCESS = 0x10006;
    //public static final int UPGRADE_VERSION_FAILURE = 0x10007;  
    
    public static final int DOWNLOAD_ON_WAIT = 0x10006;
    public static final int DOWNLOAD_ON_START = 0x10007; 
    public static final int DOWNLOAD_ON_STOP = 0x10008; 
    public static final int DOWNLOAD_ON_CANCEL = 0x10009; 
    public static final int DOWNLOAD_ON_LOAD = 0x10010;  
    public static final int DOWNLOAD_ON_SUCCESS = 0x10011; 
    public static final int DOWNLOAD_ON_FINSH = 0x10012;     
    public static final int DOWNLOAD_ON_ERROR = 0x10013; 

    
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
    
    public void registerForRequestSuccess(Handler h, int what, Object obj);
    public void unregisterForRequestSuccess(Handler h);
    public void notifyRequestSuccessRegistrants(AsyncResult ar);    
    
    public void registerForRequestFailure(Handler h, int what, Object obj);
    public void unregisterForRequestFailure(Handler h);
    public void notifyRequestFailureRegistrants(AsyncResult ar);      
    
    
    public void registerForDownloadOnSuccess(Handler h, int what, Object obj);
    public void unregisterForDownloadOnSuccess(Handler h);
    public void notifyDownloadOnSuccessRegistrants(AsyncResult ar);    
    
    public void registerForDownloadOnFinsh(Handler h, int what, Object obj);
    public void unregisterForDownloadOnFinsh(Handler h);
    public void notifyDownloadOnFinshRegistrants(AsyncResult ar);   
    
    public void registerForDownloadOnError(Handler h, int what, Object obj);
    public void unregisterForDownloadOnError(Handler h);
    public void notifyDownloadOnErrorRegistrants(AsyncResult ar);        
    
    
    public void registerForDownloadOnStart(Handler h, int what, Object obj);
    public void unregisterForDownloadOnStart(Handler h);
    public void notifyDownloadOnStartRegistrants(AsyncResult ar);   
    
    public void registerForDownloadOnStop(Handler h, int what, Object obj);
    public void unregisterForDownloadOnStop(Handler h);
    public void notifyDownloadStopRegistrants(AsyncResult ar);   
    
    
    public void registerForDownloadOnCancel(Handler h, int what, Object obj);
    public void unregisterForDownloadOnCancel(Handler h);
    public void notifyDownloadOnCancelRegistrants(AsyncResult ar);   
    
    
    public void registerForDownloadOnWaiting(Handler h, int what, Object obj);
    public void unregisterForDownloadOnWaiting(Handler h);
    public void notifyDownloadOnWaitingRegistrants(AsyncResult ar);      
    
    
    public void registerForDownloadOnLoading(Handler h, int what, Object obj);
    public void unregisterForDownloadOnLoading(Handler h);
    public void notifyDownloadOnLoadingRegistrants(AsyncResult ar);    
}
