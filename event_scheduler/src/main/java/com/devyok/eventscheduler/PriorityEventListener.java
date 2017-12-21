package com.devyok.eventscheduler;
/**
 *
 * @author DengWei
 */
public interface PriorityEventListener extends EventListener{
	/**
	 * 最大优先级
	 */
	public static final int MAX_PRIORITY = 1000;
	
	public int getPriority();
}
