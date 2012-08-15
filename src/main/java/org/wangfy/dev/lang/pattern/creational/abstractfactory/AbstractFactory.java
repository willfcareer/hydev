package org.wangfy.dev.lang.pattern.creational.abstractfactory;

import org.wangfy.dev.lang.pattern.creational.LineA;
import org.wangfy.dev.lang.pattern.creational.LineB;
import org.wangfy.dev.lang.pattern.creational.ProductFour;
import org.wangfy.dev.lang.pattern.creational.ProductOne;
import org.wangfy.dev.lang.pattern.creational.ProductThree;
import org.wangfy.dev.lang.pattern.creational.ProductTwo;

/**
 * 
 * AbstractFactory.java
 * 
 * @author fuyewang E-mail: willfcareer@sohu.com
 * @version 1.0 Creation Date：2010-4-8 下午02:16:32
 * @since 1.0
 * 
 */
public interface AbstractFactory
{
	public LineA getLineA();

	public LineB getLineB();
}

class Client
{
	private LineA lineA;

	private LineB lineB;

	public Client(AbstractFactory factory)
	{
		this.lineA = factory.getLineA();
		this.lineB = factory.getLineB();
	}

	public static void main(String[] args)
	{
		Client client = new Client(new ConcreteFactoryY());
		System.out.println(client.getLineA().lineAMethod());
		System.out.println(client.getLineB().lineBMethod());
	}

	public LineA getLineA()
	{
		return lineA;
	}

	public LineB getLineB()
	{
		return lineB;
	}

}

class ConcreteFactoryX implements AbstractFactory
{

	public LineA getLineA()
	{
		return new ProductOne();
	}

	public LineB getLineB()
	{
		return new ProductThree();
	}
}

class ConcreteFactoryY implements AbstractFactory
{

	public LineA getLineA()
	{
		return new ProductTwo();
	}

	public LineB getLineB()
	{
		return new ProductFour();
	}

}