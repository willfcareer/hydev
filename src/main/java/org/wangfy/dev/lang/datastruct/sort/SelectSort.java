package org.wangfy.dev.lang.datastruct.sort;

/**
 * 
 * SelectSort.java
 * 
 * @author fuyewang E-mail: willfcareer@sohu.com
 * @version 1.0 Creation Date：2009-12-5 下午12:48:00
 * @since 1.0
 * 
 */
public class SelectSort extends AbstractSort
{

	public void sort()
	{
		// TODO Auto-generated method stub
		int large = 0;
		int index = 1;
		for (int i = 1; i < length - 1; i++)
		{
			for (int j = 1; j <= length - i; j++)
			{
				if (data[j] > large)
				{
					index = j;
					large = data[j];
				}
			}
			/* data[index] <-->data[length -i] */
			data[0] = data[length - i];
			data[length - i] = data[index];
			data[index] = data[0];
			large = 0;
		}
	}

}
