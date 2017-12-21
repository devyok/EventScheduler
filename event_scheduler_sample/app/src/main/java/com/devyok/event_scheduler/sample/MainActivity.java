package com.devyok.event_scheduler.sample;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.devyok.eventscheduler.Event;
import com.devyok.eventscheduler.EventInterceptor;
import com.devyok.eventscheduler.EventListener;
import com.devyok.eventscheduler.EventScheduler;
import com.devyok.eventscheduler.EventSchedulerException;
import com.devyok.eventscheduler.EventType;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //拦截所有事件
        EventScheduler.getDefault().setEventInterceptor(new EventInterceptor() {
            @Override
            public boolean onIntercept(Event event) {

                if(event.getType() == EventType.DATA) {

                    Toast.makeText(MainActivity.this, "数据事件被拦截,不进行分发：" + event.getType().asType(), Toast.LENGTH_SHORT).show();

                    return true;
                }

                Toast.makeText(MainActivity.this, "事件被拦截：" + event.getType().asType(), Toast.LENGTH_SHORT).show();

                return false;
            }
        });

        listenEvent();

        customEventType();

        initViews();

    }


    private void listenEvent(){

        EventScheduler.getDefault().addEventListener(Event.obtain(EventType.UI), new EventListener() {
            @Override
            public boolean handle(Event event) {

                Thread currentThread = Thread.currentThread();
                Toast.makeText(MainActivity.this, "" + event.getData().toString() + "，tid = " + currentThread.getId() + " , tname = " + currentThread.getName(), Toast.LENGTH_SHORT).show();
                Log.i("MainActivity","" + event.getData().toString() + "，tid = " + currentThread.getId() + " , tname = " + currentThread.getName());

                return false;
            }
        });

        EventScheduler.getDefault().addEventListener(Event.obtain(100, EventType.UI), new EventListener() {
            @Override
            public boolean handle(Event event) {

                Thread currentThread = Thread.currentThread();
                Toast.makeText(MainActivity.this, "" + event.getData().toString() + "，tid = " + currentThread.getId() + " , tname = " + currentThread.getName(), Toast.LENGTH_SHORT).show();

                Log.i("MainActivity","" + event.getData().toString() + "，tid = " + currentThread.getId() + " , tname = " + currentThread.getName());

                return false;
            }
        });

        EventScheduler.getDefault().addEventListener(Event.obtain(200, EventType.SYSTEM), new EventListener() {
            @Override
            public boolean handle(Event event) {

                Thread currentThread = Thread.currentThread();
                //Toast.makeText(MainActivity.this, "" + event.getData().toString() + "，tid = " + currentThread.getId() + " , tname = " + currentThread.getName(), Toast.LENGTH_SHORT).show();
                Log.i("MainActivity","" + event.getData().toString() + "，tid = " + currentThread.getId() + " , tname = " + currentThread.getName());
                return false;
            }
        });


        EventScheduler.getDefault().addEventListener(Event.obtain(300, EventType.SERVICE), new EventListener() {
            @Override
            public boolean handle(Event event) {
                Thread currentThread = Thread.currentThread();
                //Toast.makeText(MainActivity.this, "" + event.getData().toString() + "，tid = " + currentThread.getId() + " , tname = " + currentThread.getName(), Toast.LENGTH_SHORT).show();
                Log.i("MainActivity","" + event.getData().toString() + "，tid = " + currentThread.getId() + " , tname = " + currentThread.getName());
                return false;
            }
        });

        EventScheduler.getDefault().addEventListener(Event.obtain(400, EventType.DATA), new EventListener() {
            @Override
            public boolean handle(Event event) {
                Thread currentThread = Thread.currentThread();
                //Toast.makeText(MainActivity.this, "" + event.getData().toString() + "，tid = " + currentThread.getId() + " , tname = " + currentThread.getName(), Toast.LENGTH_SHORT).show();
                Log.i("MainActivity","" + event.getData().toString() + "，tid = " + currentThread.getId() + " , tname = " + currentThread.getName());
                return false;
            }
        });
    }

    final EventType CUSTOME = new EventType() {
        @Override
        public String asType() {
            return "custom";
        }
    };

    private void customEventType(){

        EventScheduler.getDefault().addEventListener(Event.obtain(100, CUSTOME), new EventListener() {
            @Override
            public boolean handle(Event event) {
                Thread currentThread = Thread.currentThread();
                Log.i("MainActivity","" + event.getData().toString() + "，tid = " + currentThread.getId() + " , tname = " + currentThread.getName());
                return false;
            }
        });
    }


    private void initViews(){

        this.findViewById(R.id.ui_event1).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                Event uievent = Event.obtain(EventType.UI);

                uievent.setData("ui事件");

                try {
                    EventScheduler.getDefault().schedul(uievent);
                } catch (EventSchedulerException e) {
                    e.printStackTrace();
                }
            }
        });

        this.findViewById(R.id.ui_event).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                Event uievent = Event.obtain(100, EventType.UI);

                uievent.setData("code为100的UI事件");

                try {
                    EventScheduler.getDefault().schedul(uievent);
                } catch (EventSchedulerException e) {
                    e.printStackTrace();
                }
            }
        });

        this.findViewById(R.id.system_event).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                Event systemevent = Event.obtain(200, EventType.SYSTEM);
                systemevent.setData("code为200的system事件");

                try {
                    EventScheduler.getDefault().schedul(systemevent);
                } catch (EventSchedulerException e) {
                    e.printStackTrace();
                }
            }
        });

        this.findViewById(R.id.service_event).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                Event serviceevent = Event.obtain(300, EventType.SERVICE);
                serviceevent.setData("code为300的service事件");
                try {
                    EventScheduler.getDefault().schedul(serviceevent);
                } catch (EventSchedulerException e) {
                    e.printStackTrace();
                }
            }
        });

        this.findViewById(R.id.data_event).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                Event dataevent = Event.obtain(400, EventType.DATA);
                dataevent.setData("code为400的data事件");

                try {
                    EventScheduler.getDefault().schedul(dataevent);
                } catch (EventSchedulerException e) {
                    e.printStackTrace();
                }
            }
        });

        this.findViewById(R.id.custom_event).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                Event dataevent = Event.obtain(100, CUSTOME);
                dataevent.setData("code为100的custome事件");

                try {
                    EventScheduler.getDefault().schedul(dataevent);
                } catch (EventSchedulerException e) {
                    e.printStackTrace();
                }
            }
        });



    }


}
