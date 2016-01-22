
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

    protected RegistrantList mRequestSuccessRegistrants = new RegistrantList();
    protected RegistrantList mRequestFailureRegistrants = new RegistrantList();
    protected RegistrantList mDownloadOnFinshRegistrants = new RegistrantList();
    protected RegistrantList mDownloadOnSuccessRegistrants = new RegistrantList();
    protected RegistrantList mDownloadOnErrorRegistrants = new RegistrantList();
    protected RegistrantList mDownloadOnLoadingRegistrants = new RegistrantList();
    protected RegistrantList mDownloadOnStartRegistrants = new RegistrantList();
    protected RegistrantList mDownloadOnStopRegistrants = new RegistrantList();
    protected RegistrantList mDownloadOnWaitingRegistrants = new RegistrantList();
    protected RegistrantList mDownloadOnCancelRegistrants = new RegistrantList();

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

    @Override
    public void registerForRequestSuccess(Handler h, int what, Object obj) {
        // TODO Auto-generated method stub
        Registrant r = new Registrant(h, what, obj);
        mRequestSuccessRegistrants.add(r);
    }

    @Override
    public void unregisterForRequestSuccess(Handler h) {
        // TODO Auto-generated method stub
        mRequestSuccessRegistrants.remove(h);
    }

    @Override
    public void notifyRequestSuccessRegistrants(AsyncResult ar) {
        // TODO Auto-generated method stub
        mRequestSuccessRegistrants
                .notifyRegistrants(ar);
    }

    @Override
    public void registerForRequestFailure(Handler h, int what, Object obj) {
        // TODO Auto-generated method stub
        Registrant r = new Registrant(h, what, obj);
        mRequestFailureRegistrants.add(r);
    }

    @Override
    public void unregisterForRequestFailure(Handler h) {
        // TODO Auto-generated method stub
        mRequestFailureRegistrants.remove(h);
    }

    @Override
    public void notifyRequestFailureRegistrants(AsyncResult ar) {
        // TODO Auto-generated method stub
        mRequestFailureRegistrants
                .notifyRegistrants(ar);
    }

    @Override
    public void registerForDownloadOnFinsh(Handler h, int what, Object obj) {
        // TODO Auto-generated method stub

        Registrant r = new Registrant(h, what, obj);
        mDownloadOnFinshRegistrants.add(r);
    }

    @Override
    public void unregisterForDownloadOnFinsh(Handler h) {
        // TODO Auto-generated method stub
        mDownloadOnFinshRegistrants.remove(h);
    }

    @Override
    public void notifyDownloadOnFinshRegistrants(AsyncResult ar) {
        // TODO Auto-generated method stub
        mDownloadOnFinshRegistrants
                .notifyRegistrants(ar);
    }

    @Override
    public void registerForDownloadOnError(Handler h, int what, Object obj) {
        // TODO Auto-generated method stub

        Registrant r = new Registrant(h, what, obj);
        mDownloadOnErrorRegistrants.add(r);
    }

    @Override
    public void unregisterForDownloadOnError(Handler h) {
        // TODO Auto-generated method stub
        mDownloadOnErrorRegistrants.remove(h);
    }

    @Override
    public void notifyDownloadOnErrorRegistrants(AsyncResult ar) {
        // TODO Auto-generated method stub
        mDownloadOnErrorRegistrants
                .notifyRegistrants(ar);
    }

    @Override
    public void registerForDownloadOnLoading(Handler h, int what, Object obj) {
        // TODO Auto-generated method stub
        Registrant r = new Registrant(h, what, obj);
        mDownloadOnLoadingRegistrants.add(r);
    }

    @Override
    public void unregisterForDownloadOnLoading(Handler h) {
        // TODO Auto-generated method stub
        mDownloadOnLoadingRegistrants.remove(h);
    }

    @Override
    public void notifyDownloadOnLoadingRegistrants(AsyncResult ar) {
        // TODO Auto-generated method stub
        mDownloadOnLoadingRegistrants
                .notifyRegistrants(ar);
    }

    @Override
    public void registerForDownloadOnStart(Handler h, int what, Object obj) {
        // TODO Auto-generated method stub
        Registrant r = new Registrant(h, what, obj);
        mDownloadOnStartRegistrants.add(r);
    }

    @Override
    public void unregisterForDownloadOnStart(Handler h) {
        // TODO Auto-generated method stub
        mDownloadOnStartRegistrants.remove(h);
    }

    @Override
    public void notifyDownloadOnStartRegistrants(AsyncResult ar) {
        // TODO Auto-generated method stub
        mDownloadOnStartRegistrants
                .notifyRegistrants(ar);
    }

    @Override
    public void registerForDownloadOnStop(Handler h, int what, Object obj) {
        // TODO Auto-generated method stub
        Registrant r = new Registrant(h, what, obj);
        mDownloadOnStopRegistrants.add(r);
    }

    @Override
    public void unregisterForDownloadOnStop(Handler h) {
        // TODO Auto-generated method stub
        mDownloadOnStopRegistrants.remove(h);
    }

    @Override
    public void notifyDownloadStopRegistrants(AsyncResult ar) {
        // TODO Auto-generated method stub
        mDownloadOnStopRegistrants
                .notifyRegistrants(ar);
    }

    @Override
    public void registerForDownloadOnCancel(Handler h, int what, Object obj) {
        // TODO Auto-generated method stub
        Registrant r = new Registrant(h, what, obj);
        mDownloadOnCancelRegistrants.add(r);
    }

    @Override
    public void unregisterForDownloadOnCancel(Handler h) {
        // TODO Auto-generated method stub
        mDownloadOnCancelRegistrants.remove(h);
    }

    @Override
    public void notifyDownloadOnCancelRegistrants(AsyncResult ar) {
        // TODO Auto-generated method stub
        mDownloadOnCancelRegistrants
                .notifyRegistrants(ar);
    }

    @Override
    public void registerForDownloadOnWaiting(Handler h, int what, Object obj) {
        // TODO Auto-generated method stub
        Registrant r = new Registrant(h, what, obj);
        mDownloadOnWaitingRegistrants.add(r);
    }

    @Override
    public void unregisterForDownloadOnWaiting(Handler h) {
        // TODO Auto-generated method stub
        mDownloadOnWaitingRegistrants.remove(h);
    }

    @Override
    public void notifyDownloadOnWaitingRegistrants(AsyncResult ar) {
        // TODO Auto-generated method stub
        mDownloadOnWaitingRegistrants
                .notifyRegistrants(ar);
    }

    @Override
    public void registerForDownloadOnSuccess(Handler h, int what, Object obj) {
        // TODO Auto-generated method stub
        Registrant r = new Registrant(h, what, obj);
        mDownloadOnSuccessRegistrants.add(r);

    }

    @Override
    public void unregisterForDownloadOnSuccess(Handler h) {
        // TODO Auto-generated method stub
        mDownloadOnSuccessRegistrants.remove(h);
    }

    @Override
    public void notifyDownloadOnSuccessRegistrants(AsyncResult ar) {
        // TODO Auto-generated method stub
        mDownloadOnSuccessRegistrants.notifyRegistrants(ar);
    }

}
