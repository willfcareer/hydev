package org.wangfy.dev.network.thread;

import java.util.concurrent.TimeUnit;

/**
 * 
 * BasicThreadExample.java
 * 
 * @author fuyewang E-mail: willfcareer@sohu.com
 * @version 1.0 Creation Date：2010-6-23 上午10:57:07
 * @since 1.0
 * 
 */
public class BasicThreadExample
{
	private int i = 0;

	private synchronized void increment()
	{
		i++;
		System.out.println(Thread.currentThread().getName()+ "[Increment]i = " + i);
	}

	private synchronized void decrement()
	{
		i--;
		System.out.println(Thread.currentThread().getName()+ "[Decrement]i = " + i);
	}

	public static void main(String[] args)
	{
		BasicThreadExample example = new BasicThreadExample();
		Increment in = example.new Increment();
		Decrement de = example.new Decrement();
		in.start();
		de.start();
	}

	class Increment extends Thread
	{
		@Override
		public void run()
		{
			while (true)
			{
				increment();
				try
				{
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	class Decrement extends Thread
	{
		@Override
		public void run()
		{
			while (true)
			{
				decrement();
				try
				{
					TimeUnit.SECONDS.sleep(2);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

}
