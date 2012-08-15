package org.wangfy.dev.lang.pattern.creational.factorymethod;

import org.wangfy.dev.lang.pattern.creational.LineA;
import org.wangfy.dev.lang.pattern.creational.ProductOne;
import org.wangfy.dev.lang.pattern.creational.ProductTwo;

/**
 * 
 * FactoryMethod.java
 * 
 * @author fuyewang E-mail: willfcareer@sohu.com
 * @version 1.0 Creation Date：2010-4-8 下午01:57:35
 * @since 1.0
 * 
 */
public class FactoryMethod
{

}

class Client
{
	public static void main(String[] args)
	{
		AbstractFactory factory = new FactoryOne();
		LineA line = factory.getLineA();
		System.out.println(line.lineAMethod());
	}
}

abstract class AbstractFactory
{
	public abstract LineA getLineA();
}

class FactoryOne extends AbstractFactory
{
	@Override
	public LineA getLineA()
	{
		return new ProductOne();
	}
}

class FactoryTwo extends AbstractFactory
{

	@Override
	public LineA getLineA()
	{
		return new ProductTwo();
	}

}