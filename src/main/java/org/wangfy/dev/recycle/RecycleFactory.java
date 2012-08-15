package org.wangfy.dev.recycle;

import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public enum RecycleFactory implements Recycle {

	RECYCLE;

	private final Log log = LogFactory.getLog(RecycleFactory.class);
	
	private int maxSize = Recycle.DEFAULT_MAX_SIZE;

	/**
	 * private recyce pool
	 */
	private Map<Class<? extends Recyclable>, Stack<Recyclable>> recyclablePool = new ConcurrentHashMap<Class<? extends Recyclable>, Stack<Recyclable>>();

	private Map<Class<? extends Recyclable>, Stack<Recyclable>> getRecyclablePool() {
		return recyclablePool;
	}

	/**
	 * clear the recycle
	 */
	public void destory() {
		getRecyclablePool().clear();
		log.debug("recycle pool clear finished");
	}

	/**
	 * recycle an recyclable instance
	 */
	public void recycle(Recyclable r) {
		r.clear();
		Class<? extends Recyclable> clazz = r.getClass();
		if (!getRecyclablePool().containsKey(clazz)) {
			Stack<Recyclable> stack = new Stack<Recyclable>();
			stack.push(r);
			getRecyclablePool().put(clazz, stack);
		} else {
			Stack<Recyclable> stack = getRecyclablePool().get(clazz);
			if (stack.size() < getMaxSize()) {
				stack.push(r);
			} else {
				log.info("recyclable instance reach limit, didn't recycle " + clazz.getName());
			}
		}
	}

	/**
	 * restore an recyclable instance
	 */
	public Recyclable restore(Recyclable r) {
		Class<? extends Recyclable> clazz = r.getClass();
		Stack<Recyclable> stack = getRecyclablePool().get(clazz);
		if (stack != null && stack.size() > 0) {
			Recyclable recyclable = stack.pop();
			log.debug("restore _Zuhe  recyclable instance " + r.getClass().getName() + " current size " + stack.size());
			return recyclable;
		} else {
			log.info("no recyclable instance " + clazz.getName());
			return null;
		}
	}

	public Recyclable restore(Class<? extends Recyclable> clazz) {
		Stack<Recyclable> stack = getRecyclablePool().get(clazz);
		if (stack != null && stack.size() > 0) {
			Recyclable recyclable = stack.pop();
			log.debug("restore _Zuhe  recyclable instance " + clazz.getName() + " current size " + stack.size());
			return recyclable;
		} else {
			log.info("no recyclable instance " + clazz.getName());
			return null;
		}

	}

	public int getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(int size) {
		maxSize = size;
	}

}
