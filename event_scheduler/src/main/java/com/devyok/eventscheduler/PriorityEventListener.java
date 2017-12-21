package com.devyok.eventscheduler;
/**
 * 支持优先级的事件监听 
 * @author wei.deng
 */
public interface PriorityEventListener extends EventListener{
	/**
	 * 最大优先级
	 */
	public static final int MAX_PRIORITY = 1000;
	
	public int getPriority();
}
