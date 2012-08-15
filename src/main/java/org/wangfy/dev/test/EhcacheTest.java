package org.wangfy.dev.test;

import java.io.Serializable;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

public class EhcacheTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CacheManager cacheMgr = new CacheManager();
		
		Ehcache cache = cacheMgr.getEhcache("colors");
		for(int i = 0; i < 10000; i++){
			Element e = new Element(String.valueOf(i),new Bean(i));
			cache.put(e);
		}
		long count = cache.getMemoryStoreSize();
		System.out.println("count = "+ count);
		
//		long start = System.nanoTime();
		long start = System.currentTimeMillis();
//		Bean bean = (Bean) cache.get(String.valueOf(0)).getValue();
		for(int i = 0; i < 10000; i++){
			cache.get(String.valueOf(i));			
		}
//		System.out.println(bean.getI());
//		long end = System.nanoTime();d
		long end = System.currentTimeMillis();
		System.out.println(end - start);
	}

}

class Bean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int i;
	
	public Bean(int i) {
		super();
		this.i = i;
	}

	public int getI(){
		return i;
	}
}
