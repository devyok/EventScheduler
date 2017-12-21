package com.devyok.eventscheduler;
/**
 *
 * @author DengWei
 */
public interface EventInterceptor {
	public boolean onIntercept(Event e);
}
