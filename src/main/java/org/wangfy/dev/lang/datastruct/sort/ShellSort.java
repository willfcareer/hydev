package org.wangfy.dev.lang.datastruct.sort;

/**
 * 
 * ShellSort.java
 * 
 * @author fuyewang E-mail: willfcareer@sohu.com
 * @version 1.0 Creation Date：2009-12-5 下午02:52:13
 * @since 1.0
 * 
 */
public class ShellSort extends AbstractSort
{
	public void sort()
	{
		// TODO Auto-generated method stub
		int[] ds = { 20, 5, 3, 1 };
		for (int i = 0; i < ds.length; i++)
		{
			shell(ds[i]);
		}
	}

	private void shell(int d)
	{
		for (int i = 1; i <= d; i++)
		{
			for (int j = i; j < length; j = j + d)
			{
				data[0] = data[j];
				int z = j;
				while ((z - d) >= 0 && data[0] < data[z - d])
				{
					data[z] = data[z - d];
					z = z - d;
				}
				data[z] = data[0];
			}
		}
	}

}
