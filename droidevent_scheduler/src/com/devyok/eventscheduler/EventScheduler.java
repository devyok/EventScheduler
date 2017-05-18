package com.devyok.eventscheduler;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>@author wei.deng</p>
 * 
 * <p>�¼�������<br>
 * 1.֧�����������¼�<br>
 * 2.֧���¼����ȼ�
 * </p>
 * 
 * <p>������<br>
 * <p>
 * EventScheduler eventScheduler = EventScheduler.getDefault();<br>
 * eventScheduler.addEventListener(Event.obtain(5, EventType.DATA),new PriorityEventListener() {<br>
			
			<p>@Override<br>
			public boolean handle(Event event) {<br>
				//handle this event <br>
				return false;<br>
			}<br>
			<p>@Override<br>
			public int getPriority() {<br>
				return PriorityEventListener.MAX_PRIORITY;<br>
			}<br>
		});<br></p>
		eventScheduler.addEventListener(Event.obtain(6, EventType.SERVICE),new EventListener() {<br>
			
			<p>@Override<br>
			public boolean handle(Event event) {<br>
				//handle this event <br>
				return false;<br>
			}<br>
		});<br>
		<p>
		try {<br>
			eventScheduler.schedul(Event.obtain(5, EventType.DATA));<br>
			eventScheduler.schedul(Event.obtain(6, EventType.SERVICE));<br>
		} catch (EventSchedulerException e) {<br>
			e.printStackTrace(); //handle this<br>
		}<br>
 * 
 */
public final class EventScheduler {

	private ConcurrentHashMap<Event, SchedulerImpl> mSchedulers = new ConcurrentHashMap<Event, SchedulerImpl>();

	private EventInterceptor mEventInterceptor;
	
	private static final EventScheduler sDefault = new EventScheduler();
	
	public static EventScheduler getDefault(){
		return sDefault;
	}
	
	private void check(Event event){
		int code = event.getCode();
		if(code <= 0){
			throw new EventSchedulerRuntimeException("event.code must be greater than 0");
		}
		
		EventType type = event.getType();
		if(type == null){
			throw new EventSchedulerRuntimeException("event.type must not be nu");
		}
	}
	
	public void setEventInterceptor(EventInterceptor eventInterceptor) {
		this.mEventInterceptor = eventInterceptor;
	}
	
	public void addEventListener(Event event,EventListener o) {
		
		check(event);
		
		SchedulerImpl scheduler = mSchedulers.get(event);
		
		if(scheduler==null){
			scheduler = new SchedulerImpl();
			mSchedulers.put(event, scheduler);
		}
		scheduler.addEventListener(o);
		
	}
	
	public void deleteEventListener(Event event,EventListener o) {
		check(event);
		SchedulerImpl scheduler = mSchedulers.get(event);
		if(scheduler!=null){
			scheduler.deleteEventListener(o);
		}
	}

	public void deleteEventListeners(Event event) {
		check(event);
		SchedulerImpl scheduler = mSchedulers.get(event);
		if(scheduler!=null){
			scheduler.deleteEventListeners();
		}
	}
	
	public boolean schedul(Event event) throws EventSchedulerException{
		
		check(event);
		
		try {
			if(interceptEventBeforeSchedul(event)){
				return false;
			}
			
			SchedulerImpl scheduler = mSchedulers.get(event);
			
			if(scheduler!=null){
				scheduler.notifyEventListeners(event);
			}
			
			return true;
		} catch(Exception e){
			throw new EventSchedulerException(" scheduler error " + event.toString() ,e);
		}
	}
	
	private boolean interceptEventBeforeSchedul(Event event){
		
		if(mEventInterceptor!=null){
			synchronized (mEventInterceptor) {
				return mEventInterceptor.onIntercept(event);
			}
		}
		return false;
	}
	
	final class SchedulerImpl implements Comparator<EventListener>{

		private boolean changed = false;
		private Vector<EventListener> obs;
		
		public SchedulerImpl() {
			obs = new Vector<EventListener>();
		}
		
		public void addEventListener(EventListener o) {
			if (o == null)
				throw new NullPointerException();
			
			if (!obs.contains(o)) {
				obs.addElement(o);
				Collections.sort(obs, this);
			}
		}

		public void deleteEventListener(EventListener o) {
			obs.removeElement(o);
		}

		public void notifyEventListeners(Event event) {
			
			setChanged();
			
			Object[] arrLocal;

			synchronized (this) {
				
				if (!changed)
					return;
				arrLocal = obs.toArray();
				clearChanged();
				
			}
			
			for (int i = 0;i <= arrLocal.length - 1; i++) {
				((EventListener) arrLocal[i]).handle(event);
			}
			
		}

		
		public void deleteEventListeners() {
			obs.removeAllElements();
		}
		
		protected synchronized void setChanged() {
			changed = true;
		}
		
		protected void clearChanged() {
			changed = false;
		}

		public boolean hasChanged() {
			return changed;
		}

		
		public int countListeners() {
			return obs.size();
		}

		@Override
		public int compare(EventListener o1, EventListener o2) {
			
			int e1Priority = 0;
			int e2Priority = 0;
			
			if(o1 instanceof PriorityEventListener) {
				e1Priority = ((PriorityEventListener) o1).getPriority();
			} 
			
			if(o2 instanceof PriorityEventListener) {
				e2Priority = ((PriorityEventListener) o2).getPriority();
			} 
			
			if(e1Priority < e2Priority) {  
                return 1;  
            } else if(e1Priority == e2Priority) {  
                return 0;  
            } else {  
                return -1;  
            } 
		}
		
		
	}
	
}
