package com.devyok.eventscheduler;
/**
 * 事件类型
 * @author wei.deng 
 */
public interface EventType {
	
	public static EventType UI = new UIType();
	public static EventType SERVICE = new ServiceType();
	public static EventType DATA = new DataType();
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
