package org.wangfy.dev.lang.innerclass;

import java.lang.reflect.Modifier;

/**
 * 
 * OutterClass.java
 * 
 * @author fuyewang E-mail: willfcareer@sohu.com
 * @version 1.0 Creation Date：2009-11-25 下午09:18:12
 * @since 1.0
 * 
 */
public class OutterClass
{
	private int i = 0;
	private String s = "abc";

	/*
	 * 静态内嵌类(static nested class) 该类型的修饰符一般为private,protected,默认等，如果为public则与普通的共有类基本上没有什么区别了
	 */
	protected static class StaticNestedClass
	{
		private void run()
		{
			System.out.println(this.toString());
			// OutterClass.this.yell();//静态内嵌类实例没有外部类实例的引用
		}
	}

	/*
	 * 内部类 不能脱离外部类而存在。如果为private则只能在内部使用。如果为其他类型则可以在相应的范围内可见
	 */
	private class InnerClass
	{
		private void run()
		{
			System.out.println(this.toString());
			OutterClass.this.run();// 内部类实例显式使用外部类实例的方式
		}
	}
	
	/*
	 * 外部类的方法
	 */
	private void run()
	{

	}

	private void run(int i) // 如果要在匿名内部类中使用该参数，则应该为final类型
	{
		/*
		 * 局部类(local class),当然同样属于内部类的范畴 但Local Class 不能使用private,protected以及public等修饰 只能用abstract或者final修饰，当然二者不能并存 如果不加任何修饰符则既非abstract,也非final
		 */
		final class LocalClass
		{
			public void run()
			{
				/*
				 * 检查LocalClass的修饰符
				 */
				int mod = LocalClass.class.getModifiers();
				boolean isAbstract = Modifier.isAbstract(mod);
				boolean isFinal = Modifier.isFinal(mod);
				
				OutterClass outterClass = OutterClass.this;// 局部类同样具有外部类的引用
				System.out.println(this.toString());
			}
		}
		/*
		 * 匿名内部类(Anonymous InnerClass)
		 */
		new Runnable()
		{
			public void run()
			{
				/*
				 * 匿名内部类不能引用或者改变从外部类传入的非final参数。如需引用必须为final类型
				 */
				// i = 3;
				// System.out.println(i);
				/*
				 * 以下代码修改外部类成员变量的值 经证明，匿名内部类可以引用或者修改外部类成员的非final变量（final类型还用废话吗？）
				 */
				OutterClass.this.i = 2;
				OutterClass.this.s = "bcd";
				System.out.println(OutterClass.this.i);
				System.out.println(OutterClass.this.s);
				System.out.println(this.toString());
			}
		}.run();
		LocalClass lc = new LocalClass();
		InnerClass ic = new InnerClass();
		StaticNestedClass sc = new StaticNestedClass();
		lc.run();
		ic.run();
		sc.run();
	}

	public static void main(String[] args)
	{
		OutterClass oc = new OutterClass();
		oc.run(2);
		System.out.println(oc.i);
		System.out.println(oc.s);
	}
}
