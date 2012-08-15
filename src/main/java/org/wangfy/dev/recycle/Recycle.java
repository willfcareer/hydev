package org.wangfy.dev.recycle;
/**
 * @author wangfy
 * @Email: willfcareer@hotmaill.com
 * @Version 1.0
 * @Date:2008-11-20 下午09:42:30
 * @Since 1.0
 */
public interface Recycle {

	/**
	 * max number recycle object
	 */
	public static final int DEFAULT_MAX_SIZE = 5000;

	public static final int DEFAULT_INIT_SIZE = 50;

	/**
	 * recycle an instance of recyclable
	 * @param r
	 */
	public void recycle(Recyclable r);

	/**
	 * restore an instance of recyclable
	 * @param clazz
	 * @return
	 */
	public Recyclable restore(Class<? extends Recyclable> clazz);
	
	/**
	 * clear recycle when shutdown
	 */
	public void destory();
	
	/**
	 * set recycle max size
	 * 
	 * @param size
	 */
	public void setMaxSize(int size);
}
