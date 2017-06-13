package com.devyok.eventscheduler;
/**
 * 事件拦截 
 * @author wei.deng
 */
public interface EventInterceptor {
	public boolean onIntercept(Event e);
}
