package org.wangfy.dev.lang.pattern.creational.simplefactory;

import org.wangfy.dev.lang.pattern.creational.LineA;
import org.wangfy.dev.lang.pattern.creational.ProductTwo;

/**
 * 
 * SimpleFactory.java
 * 
 * @author fuyewang E-mail: willfcareer@sohu.com
 * @version 1.0 Creation Date：2010-4-8 下午01:24:06
 * @since 1.0
 * 
 */
public class SimpleFactory
{
	public static LineA getProduct(String name) throws ClassNotFoundException, InstantiationException, IllegalAccessException
	{
		return (LineA) Class.forName(name).newInstance();
	}
}

class Client
{
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException
	{
		LineA lineOne = SimpleFactory.getProduct(ProductTwo.class.getName());
		System.out.println(lineOne.lineAMethod());
	}
}
