package org.wangfy.dev.lang.pattern.multithread;

/**
 * 
 * Future.java
 * 
 * @author fuyewang E-mail: willfcareer@sohu.com
 * @version 1.0 Creation Date：2010-6-24 下午03:43:06
 * @since 1.0
 * 
 */
public class Future
{
	final Future future = new Future();

	public Future request()
	{
		new Thread()
		{
			@Override
			public void run()
			{
				// 耗时动作
				RealSubject subject = new RealSubject();
				future.setRealSubject(subject);
			}

		}.start();
		return future;
	}

	protected void setRealSubject(RealSubject subject)
	{

	}

	public class RealSubject
	{

	}
}
