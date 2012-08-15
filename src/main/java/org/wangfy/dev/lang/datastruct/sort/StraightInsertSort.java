package org.wangfy.dev.lang.datastruct.sort;

/**
 * 
 * StraightInsertSort.java
 * 
 * @author fuyewang E-mail: willfcareer@sohu.com
 * @version 1.0 Creation Date：2009-12-5 上午11:42:42
 * @since 1.0
 * 
 */
public class StraightInsertSort extends AbstractSort
{

	public void sort()
	{
		// TODO Auto-generated method stub
		for (int i = 2; i < length; i++)
		{
			data[0] = data[i];
			int index = i - 1;
			while (data[index] > data[0])
			{
				data[index + 1] = data[index];
				index--;
			}
			data[index + 1] = data[0];
		}
		for (int i = 2; i < length; i++)
		{
			data[0] = data[i];
			int index = i - 1;
			while(data[index]> data[0])
			{
				data[index+1]=data[index];
				index--;
			}
		}
	}

}
