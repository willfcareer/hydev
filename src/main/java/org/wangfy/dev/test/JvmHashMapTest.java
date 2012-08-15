package org.wangfy.dev.test;

import java.util.HashMap;
import java.util.Map;

public class JvmHashMapTest {

	public static void main(String[] args) {
		Map<String,Bean> map = new HashMap<String,Bean>();
		for(int i = 0; i < 1000; i++){
			map.put(String.valueOf(i), new Bean(i));
		}
		long start = System.nanoTime();
//		long start = System.currentTimeMillis();
		for(int i = 0; i < 1000; i++){
			Bean bean = map.get(String.valueOf(i));			
		}
//		System.out.println(bean.getI());
		long end = System.nanoTime();
//		long end = System.currentTimeMillis();
		System.out.println(end - start);
	}

}
