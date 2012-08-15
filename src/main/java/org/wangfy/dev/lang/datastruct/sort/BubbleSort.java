package org.wangfy.dev.lang.datastruct.sort;

/**
 * 
 * BubbleSort.java
 * 
 * @author fuyewang E-mail: willfcareer@sohu.com
 * @version 1.0 Creation Date：2009-12-5 下午01:33:33
 * @since 1.0
 * 
 */
public class BubbleSort extends AbstractSort
{

	public void sort()
	{
		boolean flag = true;
		for (int i = 1; i < length - 1 && flag; i++)
		{
			flag = false;
			for (int j = 1; j < length - 1 - i; j++)
			{
				if (data[j] > data[j + 1])
				{
					data[0] = data[j + 1];
					data[j + 1] = data[j];
					data[j] = data[0];
					flag = true;
				}
			}
		}
	}

}
