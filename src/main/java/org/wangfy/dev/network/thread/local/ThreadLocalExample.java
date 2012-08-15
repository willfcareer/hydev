package org.wangfy.dev.network.thread.local;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * ThreadLocalExample.java
 * 
 * @author fuyewang E-mail: willfcareer@sohu.com
 * @version 1.0 Creation Date：2010-9-8 下午09:45:55
 * @since 1.0
 * 
 */
public class ThreadLocalExample {
	private static final AtomicInteger uniqueId = new AtomicInteger(0);

	private static final ThreadLocal<Integer> uniqueNum = new ThreadLocal<Integer>() {
		@Override
		protected Integer initialValue() {
			return uniqueId.getAndIncrement();
		}
	};

	public static int getCurrentThreadId() {
		return uniqueNum.get();
	}

	public static void main(String[] args) {

		for (int i = 0; i < 2; i++) {
			new Thread() {
				@Override
				public void run() {
					int id = ThreadLocalExample.getCurrentThreadId();
					System.out.println("Current Thread Id = " + id);
				}
			}.start();
		}
	}
}
