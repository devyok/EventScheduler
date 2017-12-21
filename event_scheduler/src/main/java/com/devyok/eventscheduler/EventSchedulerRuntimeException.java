package com.devyok.eventscheduler;
/**
 * 事件调度运行时异常
 * @author wei.deng
 */
public class EventSchedulerRuntimeException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EventSchedulerRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public EventSchedulerRuntimeException(String message) {
		super(message);
	}
	
	
	
}
