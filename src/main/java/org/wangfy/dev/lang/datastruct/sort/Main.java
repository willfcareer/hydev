package org.wangfy.dev.lang.datastruct.sort;

/**
 * 
 * Main.java
 * 
 * @author fuyewang E-mail: willfcareer@sohu.com
 * @version 1.0 Creation Date：2009-12-5 上午11:35:22
 * @since 1.0
 * 
 */
public class Main
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		Sort instance;
		instance = new SelectSort();
		instance = new StraightInsertSort();
		instance = new BinaryInsertSort();
		instance = new HeapSort();
		instance = new BubbleSort();
		instance = new QuickSort();
		instance.publish();
		instance.sort();
		instance.publish();
	}

}
