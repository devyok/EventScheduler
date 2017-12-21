package com.devyok.eventscheduler;
/**
 *
 * @author DengWei
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
