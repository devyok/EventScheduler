package com.devyok.eventscheduler;
/**
 * �¼����� 
 * @author wei.deng
 */
public interface EventInterceptor {
	public boolean onIntercept(Event e);
}
