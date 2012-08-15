package org.wangfy.dev.lang.datastruct.sort;

/**
 * 
 * AbstractSort.java
 * 
 * @author fuyewang E-mail: willfcareer@sohu.com
 * @version 1.0 Creation Date：2009-12-5 上午11:41:28
 * @since 1.0
 * 
 */
public abstract class AbstractSort implements Sort
{
	// int[] data = { 0, 12, 32, 45, 2, 13, 57, 29, 11, 34, 21, 42, 15, 90 };
	int[] data = { 0, 6, 5, 4, 3, 2, 1 };
	
	int length = data.length;
	
	int compareCount = 0;
	
	int moveCount = 0;

	public void publish()
	{
		// TODO Auto-generated method stub
		System.out.println("Compare Count = " + compareCount);
		for (int i = 1; i < length; i++)
		{
			System.out.print(data[i] + ",");
		}
		System.out.println();
	}

}
