
package com.chaowei.mobileguard.tracker;



import android.os.Handler;

public class HandlerTracker implements BaseTracker {

    private static HandlerTracker sInstance;
    
    public static final HandlerTracker getTracker() {
        if (sInstance == null) {
            sInstance = new HandlerTracker();
        }
        return sInstance;
    }
    
    @Override
    public void registerForRoketNomal(Handler h, int what, Object obj) {
        // TODO Auto-generated method stub
        Registrant r = new Registrant(h, what, obj);
        mRoketNomalRegistrants.add(r);
    }

    @Override
    public void unregisterForRoketNomal(Handler h) {
        // TODO Auto-generated method stub
        mRoketNomalRegistrants.remove(h);
    }

    @Override
    public void registerForRoketPrepare(Handler h, int what, Object obj) {
        // TODO Auto-generated method stub
        Registrant r = new Registrant(h, what, obj);
        mRoketPrepareRegistrants.add(r);
    }

    @Override
    public void unregisterForRoketPrepare(Handler h) {
        // TODO Auto-generated method stub
        mRoketPrepareRegistrants.remove(h);
    }

    @Override
    public void registerForRoketPull(Handler h, int what, Object obj) {
        // TODO Auto-generated method stub
        Registrant r = new Registrant(h, what, obj);
        mRoketPullRegistrants.add(r);
    }

    @Override
    public void unregisterForRoketPull(Handler h) {
        // TODO Auto-generated method stub
        mRoketPullRegistrants.remove(h);
    }

    @Override
    public void registerForRoketStop(Handler h, int what, Object obj) {
        // TODO Auto-generated method stub
        Registrant r = new Registrant(h, what, obj);
        mRoketStopRegistrants.add(r);
    }

    @Override
    public void unregisterForRoketStop(Handler h) {
        // TODO Auto-generated method stub
        mRoketStopRegistrants.remove(h);
    }
}
