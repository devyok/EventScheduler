package com.devyok.eventscheduler;

import java.io.Serializable;
/**
 * 
 * @author wei.deng
 */
public abstract class Event implements Schedulable , Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int code;
	private Object data;
	private EventType type;
	private long timestamp;
	
	private Event(){
	}
	
	public static Event obtain(int code,EventType type){
		SchedulableEvent event = new SchedulableEvent();
		event.setCode(code);
		event.setType(type);
		return event;
	}
	
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		Event e = (Event) obj;
		
		int eCode = e.getCode();
		String eType = e.getType().asType();
		
		if(this.code == eCode && this.type.asType().equals(eType)) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return this.type.asType().hashCode() * this.code;
	}

	public String toString(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("event [code = " + getCode() + " , type = " + this.type.asType() + ",data = " + getData()).append("]");
		return buffer.toString();
	}

	static final class SchedulableEvent extends Event {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
	}
	
}
