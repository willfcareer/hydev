package org.wangfy.dev.lang.concurrent;

import java.io.Serializable;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 
 * ThreadPoolExample.java
 * 
 * @author fuyewang E-mail: willfcareer@sohu.com
 * @version 1.0 Creation Date：2010-7-6 下午02:50:02
 * @since 1.0
 * 
 */
public class ThreadPoolExample
{
	private static int produceTaskSleepTime = 2;
	private static int consumeTaskSleepTime = 2000;
	private static int produceTaskMaxNumber = 10;

	public static void main(String[] args)
	{
		ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2, 4, 3, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3), new ThreadPoolExecutor.DiscardOldestPolicy());
		// threadPool.prestartAllCoreThreads();
		for (int i = 0; i < produceTaskMaxNumber; i++)
		{
			try
			{
				// 产生一个任务，并将其加入到线程池
				String task = "task@ " + i;
				System.out.println("put " + task);
				threadPool.execute(new ThreadPoolTask(task));

				// 便于观察，等待一段时间
				Thread.sleep(produceTaskSleepTime);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public static class ThreadPoolTask implements Runnable, Serializable
	{
		private static final long serialVersionUID = 0;
		// 保存任务所需要的数据
		private Object threadPoolTaskData;

		public ThreadPoolTask(String tasks)
		{
			this.threadPoolTaskData = tasks;
		}

		public void run()
		{
			// 处理一个任务，这里的处理方式太简单了，仅仅是一个打印语句
			System.out.println("start .." + threadPoolTaskData);
			try
			{
				// //便于观察，等待一段时间
				Thread.sleep(consumeTaskSleepTime);
				throw new Exception("Test Exception . . .");
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			threadPoolTaskData = null;
		}

		public Object getTask()
		{
			return this.threadPoolTaskData;
		}
	}

}
