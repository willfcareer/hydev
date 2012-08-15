package org.wangfy.dev.lang.initorder;

/**
 * 
 * StaticOrderTest.java
 * 
 * @author fuyewang E-mail: willfcareer@sohu.com
 * @version 1.0 Creation Date：2010-7-27 下午01:12:02
 * @since 1.0
 * 
 */
public class StaticOrder
{
	static
	{
		showInfo();
	}

	public static void showInfo()
	{
		System.out.println(a);
		System.out.println(c);
	}

	// 静态变量
	public static TestA a = new TestA();
	public static int c = 10;

	// 静态初始化块
	static
	{
		System.out.println("静态初始化块");
	}

	// 静态变量
	public static TestB b = new TestB();

	public static void main(String[] args)
	{
		new StaticOrder();
	}
}

class TestA
{
	public TestA()
	{
		System.out.println("Test--A");
	}
}

class TestB
{
	public TestB()
	{
		System.out.println("Test--B");
	}
}
