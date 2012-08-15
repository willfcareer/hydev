package org.wangfy.dev.lang.datastruct.sort;

/**
 * 
 * HeapSort.java
 * 
 * @author fuyewang E-mail: willfcareer@sohu.com
 * @version 1.0 Creation Date：2009-12-5 下午01:03:07
 * @since 1.0
 * 
 */
public class HeapSort extends AbstractSort
{

	public void sort()
	{
		// TODO Auto-generated method stub
		createHeap();
		for (int index = length - 1; index > 1;)
		{
			data[0] = data[index];
			data[index] = data[1];
			data[1] = data[0];
			updateHeap(1, --index);
		}

	}

	private void createHeap()
	{
		int parent = (length - 1) / 2;
		int child = parent * 2;
		for (; parent > 0; parent--)
		{
			child = parent * 2;
			if ((child + 1) < length && data[child] < data[child + 1])
				child++;
			if (data[child] > data[parent])
			{
				data[0] = data[child];
				data[child] = data[parent];
				data[parent] = data[0];
				updateHeap(child, length - 1);
			}
		}
	}

	private void updateHeap(int root, int size)
	{
		int child = 2 * root;
		if (child > size)
			return;
		int large = root;
		if (child == size && data[child] > data[root])
			large = child;
		else if ((child + 1) <= size)
			if (data[child] < data[child + 1])
				large = child + 1;
			else
				large = child;
		if (large != root)
		{
			data[0] = data[large];
			data[large] = data[root];
			data[root] = data[0];
			updateHeap(large, size);
		}
	}

}
