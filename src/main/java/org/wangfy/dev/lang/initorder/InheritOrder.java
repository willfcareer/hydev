package org.wangfy.dev.lang.initorder;

/**
 * 
 * SubClass.java
 * 
 * @author fuyewang E-mail: willfcareer@sohu.com
 * @version 1.0 Creation Date：2010-7-27 下午01:17:35
 * @since 1.0
 * 
 */
class Parent
{
	// 静态变量
	public static String p_StaticField = "父类--静态变量";
	// 变量
	public String p_Field = "父类--变量";

	// 静态初始化块
	static
	{
		System.out.println(p_StaticField);
		System.out.println("父类--静态初始化块");
	}

	// 初始化块
	{
		System.out.println(p_Field);
		System.out.println("父类--初始化块");
	}

	// 构造器
	public Parent()
	{
		System.out.println("父类--构造器");
	}
}

public class InheritOrder extends Parent
{
	// 静态变量
	public static String s_StaticField = "子类--静态变量";
	// 变量
	public String s_Field = "子类--变量";
	// 静态初始化块
	static
	{
		System.out.println(s_StaticField);
		System.out.println("子类--静态初始化块");
	}
	// 初始化块
	{
		System.out.println(s_Field);
		System.out.println("子类--初始化块");
	}

	// 构造器
	public InheritOrder()
	{
		System.out.println("子类--构造器");
	}

	// 程序入口
	public static void main(String[] args)
	{
		new InheritOrder();
	}
}
