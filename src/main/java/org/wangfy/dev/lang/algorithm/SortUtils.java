package org.wangfy.dev.lang.algorithm;

/**
 * 
 * QSort.java
 * 
 * @author fuyewang E-mail: willfcareer@sohu.com
 * @version 1.0 Creation Date：2010-6-25 下午09:24:23
 * @since 1.0
 * 
 */
public class SortUtils
{
	/*
	 * Quick Sort
	 */
	public static void QSort(int[] a, int x, int y)
	{
		if (x < y)
		{
			int i = Partition1(a, x, y);
			QSort(a, x, i - 1);
			QSort(a, i + 1, y);
		}
	}

	/*
	 * 双向缩进法
	 */
	public static int Partition1(int[] a, int x, int y)
	{
		int pivot = a[x];
		while (x < y)
		{
			while (x < y && a[y] >= pivot)	y--;
			a[x] = a[y];
			while (x < y && a[x] <= pivot)	x++;
			a[y] = a[x];
		}
		a[x] = pivot;
		return x;
	}

	/*
	 * 边界交换法
	 */
	public static int Partition2(int[] a, int x, int y)
	{
		int pivot = x; // 选择x为轴，分小集合与大集合。此时状态{null|pivot|大集合}
		for (int i = x; i <= y; i++) // 遍历
		{
			if (a[i] < a[x]) // 当前元素小于轴元素，则与小集合的边界a[pivot+1]交换
			{
				swap(a, i, pivot + 1);// (注:不要交换pivot,其值变化将会引发后续比较错误。也可以先将其交换至最后)
				pivot++;// 轴向右移动一位(每移动一位说明小集合中多一个元素)
			}
		}
		swap(a, x, pivot);// 最后轴与小集合边界交换，构成{小集合|pivot|大集合}
		return pivot;
	}

	public static void swap(int[] a, int x, int y)
	{
		int t = a[x];
		a[x] = a[y];
		a[y] = t;
	}

	public static void main(String[] args)
	{
		int[] a = new int[] { 3, 4, 2, 1, 6, 5 };
		QSort(a, 0, a.length - 1);
		for (int i = 0; i < a.length; i++)
			System.out.print(a[i]);
		System.out.println();
	}
}
