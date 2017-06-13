package com.devyok.eventscheduler;

/**
 * 处理事件 
 * @author wei.deng
 */
public interface EventListener {

	public boolean handle(Event event);
	
}
