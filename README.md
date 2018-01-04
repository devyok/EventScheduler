[![license](http://img.shields.io/badge/license-Apache2.0-brightgreen.svg?style=flat)](https://github.com/devyok/EventScheduler/blob/master/LICENSE)
[![Release Version](https://img.shields.io/badge/release-0.0.3-brightgreen.svg)](https://jcenter.bintray.com/com/devyok/eventscheduler/event_scheduler/0.0.3/)

# EventScheduler
Android事件分发库


## 优势 ##

- 模块间解耦;
- 提高开发效率；

[看看设计](https://github.com/devyok/EventScheduler/blob/master/README_DESIGN.md)

## 如何使用 ##
[直接看实例代码](https://github.com/devyok/EventScheduler/tree/master/event_scheduler_sample)

### 第一步 ###
在gradle中引入EventScheduler

	dependencies {
    	compile 'com.devyok.eventscheduler:event_scheduler:0.0.3'
	}

### 第二步 ###
监听事件

普通优先级的事件，按照添加顺序接收

	EventScheduler.getDefault().addEventListener(Event.obtain(EventType.UI), new EventListener() {
            @Override
            public boolean handle(Event event) {
                return false;
            }
    });
	
支持优先级的事件

	EventScheduler.getDefault().addEventListener(Event.obtain(100, EventType.UI), new PriorityEventListener() {
            @Override
            public int getPriority() {
				//定义优先级
                return PriorityEventListener.MAX_PRIORITY;
            }
            @Override
            public boolean handle(Event event) {
                return false;
            }
    });

拦截所有事件

    EventScheduler.getDefault().setEventInterceptor(new EventInterceptor() {
        @Override
        public boolean onIntercept(Event event) {
			//true:将不在分发这个事件
            return false;
        }
    });

EventType默认共包含以下类似：
	
	public interface EventType {
		//如果有此事件发出时，表示有更新UI的需求，所有UI事件都会被提交到主线程的队列中。
	    EventType UI = new EventType.UIType();
		//如果有此事件发出时，表示有业务功能被完成或更新等操作，所有SERVICE事件都被提交到SERVICE线程队列中。
	    EventType SERVICE = new EventType.ServiceType();
		//如果有此事件发出时，表示有相关业务数据发生了变化(CRUD)，所有DAT事件都被提交到DATA线程队列中。
	    EventType DATA = new EventType.DataType();
		//如果有此事件发出时，表示系统相关状态发生变化等操作，所有SYSTEM事件都被提交到SYSTEM线程队列中。
	    EventType SYSTEM = new EventType.SystemType();
	}

每个EventType包含一个android.os.Looper,对应一个线程队列。

### 第三步 ###
调度一个UI事件，此事件将被运行在UI线程

	Event uievent = Event.obtain(EventType.UI);
    uievent.setData("ui事件");
    try {
        EventScheduler.getDefault().schedul(uievent);
    } catch (EventSchedulerException e) {
        e.printStackTrace();
    }


## 自定义事件类型 ##

### 定义类型 ###
	
	final EventType CUSTOME = new EventType() {
        @Override
        public String asType() {
            return "custom";
        }
    };

### 监听类型事件 ###

	EventScheduler.getDefault().addEventListener(Event.obtain(100, CUSTOME), new EventListener() {
        @Override
        public boolean handle(Event event) {
            Thread currentThread = Thread.currentThread();
            Log.i("MainActivity","" + event.getData().toString() + "，tid = " + currentThread.getId() + " , tname = " + currentThread.getName());
            return false;
        }
    });

### 调度自定义类型的事件 ###

	Event event = Event.obtain(100, CUSTOME);
    event.setData("code为100的custome事件");

    try {
        EventScheduler.getDefault().schedul(event);
    } catch (EventSchedulerException e) {
        e.printStackTrace();
    }

## 设计 ##
请参考：

![](https://raw.githubusercontent.com/devyok/EventScheduler/master/eventscheduler_design_layer.png)
![](https://raw.githubusercontent.com/devyok/EventScheduler/master/eventscheduler_design_package.png)
![](https://raw.githubusercontent.com/devyok/EventScheduler/master/eventscheduler_design_class.png)
![](https://raw.githubusercontent.com/devyok/EventScheduler/master/eventscheduler_design_seq.png)

## License ##
EventScheduler is released under the [Apache 2.0 license](https://github.com/devyok/EventScheduler/blob/master/LICENSE).

Copyright (C) 2017 DengWei.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.