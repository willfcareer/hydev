package org.wangfy.dev.lang.initorder;

/**
 * 
 * InitialOrderTest.java
 * 
 * @author fuyewang E-mail: willfcareer@sohu.com
 * @version 1.0 Creation Date：2010-7-27 下午01:07:08
 * @since 1.0
 * 
 */
public class InitialOrder
{

	// 静态变量
	public static String staticField = "静态变量";
	// 变量
	public String field = "变量";

	// 静态初始化块
	static
	{
		System.out.println(staticField);
		System.out.println("静态初始化块");
	}

	// 初始化块
	{
		System.out.println(field);
		System.out.println("初始化块");
	}

	// 构造器
	public InitialOrder()
	{
		System.out.println("构造器");
	}

	public static void main(String[] args)
	{
		new InitialOrder();
	}
}