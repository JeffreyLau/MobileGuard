
package com.chaowei.mobileguard.tracker;

import android.os.Handler;

public class AsyncTracker implements BaseTracker {

    private static AsyncTracker sInstance;

    public static final AsyncTracker getDefaultTracker() {
        if (sInstance == null) {
            sInstance = new AsyncTracker();
        }
        return sInstance;
    }

    protected RegistrantList mRoketNomalRegistrants = new RegistrantList();
    protected RegistrantList mRoketPrepareRegistrants = new RegistrantList();
    protected RegistrantList mRoketPullRegistrants = new RegistrantList();
    protected RegistrantList mRoketStopRegistrants = new RegistrantList();

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

    @Override
    public void notifyRoketNomalRegistrants(AsyncResult ar) {
        // TODO Auto-generated method stub
        mRoketNomalRegistrants
                .notifyRegistrants(ar);
    }

    @Override
    public void notifyRoketPrepareRegistrants(AsyncResult ar) {
        // TODO Auto-generated method stub
        mRoketPrepareRegistrants
                .notifyRegistrants(ar);
    }

    @Override
    public void notifyRoketPullRegistrants(AsyncResult ar) {
        // TODO Auto-generated method stub
        mRoketPullRegistrants
                .notifyRegistrants(ar);
    }

    @Override
    public void notifyRoketStopRegistrants(AsyncResult ar) {
        // TODO Auto-generated method stub
        mRoketStopRegistrants
                .notifyRegistrants(ar);
    }
}
