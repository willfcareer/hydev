package org.wangfy.dev.lang.concurrent;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ConcurrentExample {

	private final Log log = LogFactory.getLog(this.getClass());

	private Set<Object> set = Collections.synchronizedSet(new HashSet<Object>());

	private static Integer n1 = 1;

	private static volatile Integer n2 = 0;

	private static AtomicInteger n3 = new AtomicInteger();

	private void concurrentIncrement() {
		for (int i = 0; i < 2; i++) {
			Task task = new Task();
			Thread t = new Thread(task);
			t.start();
		}
	}

	public static void main(String[] args) {
		ConcurrentExample example = new ConcurrentExample();
		example.concurrentIncrement();

	}

	private class Task implements Runnable {
		public void run() {
			while (n3.get() < 100) {
				if (set.contains(n3))
					log.error("ERROR : " + n3 + " exist in set=====================>failure");
				else
					set.add(n3);
				log.debug("thread: " + Thread.currentThread().getName() + "\tcurrent n = " + n3 + "\tSet size = " + set.size());
//				n3++;
				n3.getAndIncrement();
			}
		}
	}
}
