package com.devyok.eventscheduler;
/**
 * ֧�����ȼ����¼����� 
 * @author wei.deng
 */
public interface PriorityEventListener extends EventListener{
	/**
	 * ������ȼ�
	 */
	public static final int MAX_PRIORITY = 1000;
	
	public int getPriority();
}
