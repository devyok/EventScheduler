package com.devyok.eventscheduler;


import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;

/**
 * @author DengWei
 */
final class EventQueueThread<T> extends Thread implements Callback{

    private static final int DISPATCH = 0x0001;

    Handler mHandler;
    Looper mLooper;

    private String eventType;

    public EventQueueThread(){}

    public EventQueueThread(String eventType){
        this.eventType = eventType;
        setName(eventType);
    }

    public EventQueueThread(String eventType,Looper looper){
        this(eventType);
        this.mHandler = new Handler(looper,this);
        this.mLooper = looper;
    }

    Callback<T> mCallback;

    public interface Callback<T> {
        public void handle(T e);
    }

    @Override
    public boolean handleMessage(Message msg) {

        int what = msg.what;

        if(what == DISPATCH) {
            if(mCallback!=null){
                Object obj = msg.obj;
                if(obj!=null){
                    mCallback.handle((T)obj);
                }
            }
        }

        return false;
    }

    @Override
    public void run() {
        if(isCreateNewLooper()){
            Looper.prepare();
            synchronized (this) {
                mLooper = Looper.myLooper();
                mHandler = new Handler(mLooper,this);
                notifyAll();
            }
            Looper.loop();
        }
    }

    private boolean isCreateNewLooper(){
        return (mLooper == null && mHandler == null);
    }

    @Override
    public synchronized void start() {
        if(isCreateNewLooper()){
            super.start();
        }
    }

    Handler getHandler() {

        if(mHandler!=null){
            return mHandler;
        }

        if (!isAlive()) {
            return null;
        }

        // If the thread has been started, wait until the looper has been created.
        synchronized (this) {
            while (isAlive() && mHandler == null) {
                try {
                    wait();
                } catch (InterruptedException e) {
                }
            }
        }
        return mHandler;
    }

    public boolean quit() {

        if(mLooper == Looper.getMainLooper()) {
            throw new EventSchedulerRuntimeException("main looper don't quit");
        }

        Looper looper = mLooper;
        if (looper != null) {
            looper.quit();
            return true;
        }
        return false;
    }

    boolean enqueue(T data) {
       return enqueue(data,null);
    }

    public boolean enqueue(T data,Callback callback) {

        if(callback == null){
            throw new EventSchedulerRuntimeException("callback must be not null");
        }

        this.mCallback = callback;
        Handler handler = getHandler();
        Message msg = Message.obtain(handler, DISPATCH);
        msg.obj = data;
        return handler.sendMessage(msg);
    }

}

