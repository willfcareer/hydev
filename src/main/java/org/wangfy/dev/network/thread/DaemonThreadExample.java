package org.wangfy.dev.network.thread;

import java.io.IOException;

public class DaemonThreadExample extends Thread
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		DaemonThreadExample mainThread = new DaemonThreadExample();
		mainThread.setDaemon(true);
		mainThread.start();

		System.out.println("isDaemon = " + mainThread.isDaemon());

		try
		{
			System.in.read();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void run()
	{
		// TODO Auto-generated method stub
		for (int i = 0; i < 10; i++)
		{
			try
			{
				Thread.sleep(1000);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(i);
		}
	}

}
