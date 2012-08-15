package org.wangfy.dev.lang.algorithm;

/**
 * 
 * Zuhe.java
 * 
 * @author fuyewang E-mail: willfcareer@sohu.com
 * @version 1.0 Creation Date：2009-12-9 下午09:26:02
 * @since 1.0
 * 
 */
public class Zuhe
{
	static int kk = 0;

	public static void main(String[] args)
	{
		String s = "122345";// 这里是要用到的所有数组成的一个字符串,其它字符同样适用
		char[] c = s.toCharArray();
		new Zuhe().zuhe(c, c.length, 0);
		System.out.println("可能的组合数：" + kk);
	}

	private void zuhe(char[] array, int n, int k)
	{
		if (n == k)
		{
			if (array[2] != '4') 		// 第三个位置不能出现4
			{
				String str = new String(array);
				if (str.indexOf("53") < 0 && str.indexOf("35") < 0) 		// 3，5不能连续出现
				{
					System.out.println(str);
					++kk;
				}
			}
		} else
		{
			for (int i = k; i < n; i++)
			{
				swap(array, k, i);
				zuhe(array, n, k + 1);
				swap(array, i, k);
			}
		}
	}

	private void swap(char[] a, int x, int y)
	{
		char temp = a[x];
		a[x] = a[y];
		a[y] = temp;
	}

}
