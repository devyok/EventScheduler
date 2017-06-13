package com.devyok.eventscheduler;
/**
 * 事件调度异常
 * @author wei.deng
 */
public class EventSchedulerException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EventSchedulerException(String message, Throwable cause) {
		super(message, cause);
	}

	public EventSchedulerException(String message) {
		super(message);
	}
	
	
	
}
