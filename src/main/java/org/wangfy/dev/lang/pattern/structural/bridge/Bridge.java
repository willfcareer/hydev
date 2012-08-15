package org.wangfy.dev.lang.pattern.structural.bridge;

/**
 * 
 * Bridge.java
 * 
 * @author fuyewang E-mail: willfcareer@sohu.com
 * @version 1.0 Creation Date：2010-4-8 下午10:35:41
 * @since 1.0
 * 
 */
public class Bridge
{
	public static void main(String[] args) throws ClassNotFoundException
	{
		Class.forName(HySQLDriver.class.getName());
	}

}