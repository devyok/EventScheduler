package com.devyok.eventscheduler;

import android.os.Looper;

import java.util.concurrent.ConcurrentHashMap;
/**
 *
 * @author DengWei
 */
final class EventQueueThreadFactory {

    static ConcurrentHashMap<String,EventQueueThread> map = new ConcurrentHashMap<String, EventQueueThread>();

    public static <T> EventQueueThread<T> getThread(EventType eventType){
        String eventTypeString = eventType.asType();
        EventQueueThread<T> thread = map.get(eventTypeString);
        if(thread == null){

            if(EventType.UI.asType().equals(eventTypeString)){
                thread = new EventQueueThread<T>(eventTypeString, Looper.getMainLooper());
            } else {
                thread = new EventQueueThread<T>(eventTypeString);
            }
            thread.start();
            map.put(eventType.asType(),thread);
        }
        return thread;
    }

}
