package org.wangfy.dev.lang.datastruct.sort;

/**
 * 
 * BinaryInsertSort.java
 * 
 * @author fuyewang E-mail: willfcareer@sohu.com
 * @version 1.0 Creation Date：2009-12-5 下午12:38:09
 * @since 1.0
 * 
 */
public class BinaryInsertSort extends AbstractSort
{
	public void sort()
	{
		// TODO Auto-generated method stub
		for (int i = 2; i < length; i++)
		{
			data[0] = data[i];
			int pos = search(data[i], i - 1);
			int index = i - 1;
			while (index > pos)
			{
				data[index + 1] = data[index];
				index--;
			}
			data[pos + 1] = data[0];
		}
	}

	private int search(int d, int end)
	{
		int pos = 1;
		int low = 1;
		int hight = end;
		int mid = 1;
		while (low <= hight)
		{
			mid = (low + hight) / 2;
			if (data[mid] <= d)
				low = mid + 1;
			if (data[mid] > d)
				hight = mid - 1;
		}
		pos = hight;
		return pos;
	}

}
