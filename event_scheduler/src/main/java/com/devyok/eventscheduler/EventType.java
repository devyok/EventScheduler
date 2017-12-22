package com.devyok.eventscheduler;
/**
 *
 * @author DengWei
 */
public interface EventType {
	
	
	//如果有此事件发出时，表示有更新UI的需求，所有UI事件都会被提交到主线程的队列中。
	public static EventType UI = new UIType();
	//如果有此事件发出时，表示有业务功能被完成或更新等操作，所有SERVICE事件都被提交到SERVICE线程队列中。
	public static EventType SERVICE = new ServiceType();
	//如果有此事件发出时，表示有相关业务数据发生了变化(CRUD)，所有DATA事件都被提交到DATA线程队列中。
	public static EventType DATA = new DataType();
	//如果有此事件发出时，表示系统相关状态发生变化等操作，所有SYSTEM事件都被提交到SYSTEM线程队列中。
	public static EventType SYSTEM = new SystemType();
	
	public String asType();

	public static class UIType implements EventType {

		@Override
		public String asType() {
			return "UI";
		}
		
	}
	
	public static class ServiceType implements EventType {

		@Override
		public String asType() {
			return "SERVICE";
		}
		
	}
	
	public static class DataType implements EventType {

		@Override
		public String asType() {
			return "DATA";
		}
		
	}
	
	public static class SystemType implements EventType {

		@Override
		public String asType() {
			return "SYSTEM";
		}
		
	}
	
}
