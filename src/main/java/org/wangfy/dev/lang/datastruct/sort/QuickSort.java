package org.wangfy.dev.lang.datastruct.sort;

/**
 * 
 * QuickSort.java
 * 
 * @author fuyewang E-mail: willfcareer@sohu.com
 * @version 1.0 Creation Date：2009-12-5 下午01:45:51
 * @since 1.0
 * 
 */
public class QuickSort extends AbstractSort
{

	public void sort()
	{
		// TODO Auto-generated method stub
		quickSort(1, length - 1);
	}

	private void quickSort(int start, int end)
	{
		if (start < end)
		{
			int pos = partion(start, end);
			quickSort(start, pos - 1);
			quickSort(pos + 1, end);
		}
	}

	private int partion(int start, int end)
	{
		int key = data[start];
		int low = start;
		int hight = end;
		while (low < hight)
		{
			while (data[hight] >= key && low < hight)
				hight--;
			data[low] = data[hight];

			while (data[low] <= key && low < hight)
				low++;
			data[hight] = data[low];
		}
		data[low] = key;
		return low;
	}

	void quickSort(String[] pData, int[] pDataNum, int left, int right)
	{
		int i, j;
		int iTemp;
		String middle, strTemp;
		i = left;
		j = right;
		middle = pData[(left + right) / 2];
		do
		{
			while ((pData[i].compareTo(middle) < 0) && (i < right))
				i++;
			while ((pData[j].compareTo(middle)) > 0 && (j > left))
				j--;
			if (i <= j)
			{
				strTemp = pData[i];
				pData[i] = pData[j];
				pData[j] = strTemp;

				iTemp = pDataNum[i];
				pDataNum[i] = pDataNum[j];
				pDataNum[j] = iTemp;

				i++;
				j--;
			}
		} while (i <= j);// 如果两边扫描的下标交错，就停止（完成一次）

		if (left < j)
			quickSort(pData, pDataNum, left, j);

		if (right > i)
			quickSort(pData, pDataNum, i, right);
	}
}
