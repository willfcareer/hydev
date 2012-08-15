package org.wangfy.dev.network.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StandardExecutor implements Runnable {

	private final Log log = LogFactory.getLog(getClass());

	private final int id;

	private final ReadWriteLock selectorGuard = new ReentrantReadWriteLock();

	private final Object gate = new Object();

	private BlockingQueue<RegisterTask> registerTaskQueue = new LinkedBlockingQueue<RegisterTask>();

	private BlockingQueue<WriteTask> writeTaskQueue = new LinkedBlockingQueue<WriteTask>();
	
	private BlockingQueue<WorkTask> workTaskQueue = new LinkedBlockingQueue<WorkTask>();

	volatile Selector selector;

	private volatile boolean started;

	private final Executor executor;

	public StandardExecutor(final int id, Executor executor) {
		this.id = id;
		this.executor = executor;
	}

	public void register(final int id, final SocketChannel channel) throws IOException {
		RegisterTask registerTask = new RegisterTask(id, channel);
		 registerTaskQueue.offer(registerTask);
		log.debug(Thread.currentThread().getName() + "- curt register tak queue len " + registerTaskQueue.size() + " -worker id [" + id + "]");
	}

	void startup() throws IOException {
		if (!started) {
			synchronized (gate) {
				selector = Selector.open();
				boolean succ = false;
				try {
					executor.execute(this);
					succ = true;
				} finally {
					if (!succ) {
						// Release the Selector if the execution fails.
						try {
							selector.close();
						} catch (Throwable t) {
							log.warn("Failed to close _Zuhe selector.", t);
						}
						selector = null;
						// The method will return to the caller at this point.
					}
				}
				assert selector != null && selector.isOpen();
				started = true;
			}

		}
	}

	public void run() {
		for (;;) {
			try {
				selectorGuard.writeLock().lock();
				// This empty synchronization block prevents the selector from
				// acquiring its lock.
				selectorGuard.writeLock().unlock();
				int selectedKeyCount = selector.select(500);
				// log.debug(Thread.currentThread().getName() +
				// "- ready event compareCount " + selectedKeyCount);
				executeRegisterTask();
				executeWorkTask();
				executeWriteTask();
				if (selectedKeyCount > 0)
					handleSelectedKeys(selector.selectedKeys());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void executeRegisterTask() {
		for (;;) {
			Runnable task = registerTaskQueue.poll();
			if (task == null) {
				break;
			}
			log.debug(Thread.currentThread().getName() + "- poll register task and run");
			task.run();
		}
	}

	private void executeWriteTask() {
		for (;;) {
			Runnable task = writeTaskQueue.poll();
			if (task == null) {
				break;
			}
			log.debug(Thread.currentThread().getName() + "- poll write task and run");
			task.run();
		}
	}
	
	private void executeWorkTask(){
		for (;;) {
			Runnable task = workTaskQueue.poll();
			if (task == null) {
				break;
			}
			log.debug(Thread.currentThread().getName() + "- poll work task and run");
			executor.execute(task);
		}		
	}

	private void handleSelectedKeys(Set<SelectionKey> keys) {
		Iterator<SelectionKey> it = keys.iterator();
		while (it.hasNext()) {
			SelectionKey key = it.next();
			it.remove();
			if (!key.isValid()) {
				closeChannel(key);
				continue;
			}
			if (key.isReadable()) {
				handleReadableKey(key);
			} else if (key.isWritable()) {
				handleWritableKey(key);
			} else {
				closeChannel(key);
			}
		}
	}

	private void handleReadableKey(SelectionKey key) {
		RegisterTask task = (RegisterTask) key.attachment();
		ByteBuffer buffer = task.getBuffer();
		SocketChannel channel = (SocketChannel) key.channel();
		try {
			if (buffer.remaining() <= buffer.limit() / 20)
				buffer = task.extendBuffer();
			int count = channel.read(buffer);
			if (count > 0) {
				log.debug(Thread.currentThread().getName() + "- contine receive - " + count);
			} else {
				// finish read
				buffer.flip();
				key.cancel();
				channel.socket().shutdownInput();
				log.debug(Thread.currentThread().getName() + "- finish receive  - " + buffer.remaining());
				
				WorkTask workTask = new WorkTask(task.getId(),channel,buffer);
				workTaskQueue.offer(workTask);
				
				log.debug(Thread.currentThread().getName() + "- curt work task queue len " + workTaskQueue.size() + " -worker id [" + id + "]");
			}
		} catch (IOException e) {
			closeChannel(key);
			log.debug(Thread.currentThread().getName() + "- exception happened when read data from channel", e);
		}
	}

	private void handleWritableKey(SelectionKey key) {
		WriteTask task = (WriteTask) key.attachment();
		ByteBuffer buffer = task.getBuffer();
		SocketChannel channel = (SocketChannel) key.channel();
		try {
			if (buffer.hasRemaining()) {
				channel.write(buffer);
			} else {
				buffer.clear();
				channel.socket().shutdownOutput();
				closeChannel(key);
				log.debug(Thread.currentThread().getName() + "- finish write. channel closed - " + task.getId());
			}
		} catch (IOException e) {
			closeChannel(key);
			log.debug(Thread.currentThread().getName() + "- exception happened when write data to channel", e);
		}
	}

	private void closeChannel(SelectionKey key) {
		SelectableChannel channel = key.channel();
		try {
			key.cancel();
			channel.close();
		} catch (IOException e) {
			log.debug(Thread.currentThread().getName() + "- exception happened when channel close", e);
		}
	}

	private class RegisterTask implements Runnable {

		private int id;

		private SocketChannel channel;

		private ByteBuffer buffer = ByteBuffer.allocate(1024);

		public RegisterTask(final int id, final SocketChannel channel) {
			this.id = id;
			this.channel = channel;
		}

		public void run() {
			try {
				selectorGuard.writeLock().lock();
				selector.wakeup();
				channel.register(selector, SelectionKey.OP_READ, this);
				selectorGuard.writeLock().unlock();
				log.debug(Thread.currentThread().getName() + "- register OP_READ event");
			} catch (ClosedChannelException e) {
				e.printStackTrace();
			}
		}

		private ByteBuffer extendBuffer() {
			ByteBuffer buf = ByteBuffer.allocate(buffer.capacity() * 2);
			buffer.flip();
			buf.put(buffer);
			return (buffer = buf);
		}

		public ByteBuffer getBuffer() {
			return buffer;
		}

		public int getId() {
			return id;
		}
	}

	private class WriteTask implements Runnable {

		private int id;

		private SocketChannel channel;
		
		private ByteBuffer buffer;

		public WriteTask(final int id, final SocketChannel channel, final ByteBuffer buffer) {
			this.id = id;
			this.channel = channel;
			this.buffer = buffer;
		}

		public void run() {
			try {
				selectorGuard.writeLock().lock();
				selector.wakeup();
				channel.register(selector, SelectionKey.OP_WRITE, this);
				selectorGuard.writeLock().unlock();
				log.debug(Thread.currentThread().getName() + "- register OP_WRITE event");
			} catch (ClosedChannelException e) {
				e.printStackTrace();
			}
		}
		public ByteBuffer getBuffer() {
			return buffer;
		}

		public int getId() {
			return id;
		}		
	}	
	
	private class WorkTask implements Runnable {

		private int id;

		private SocketChannel channel;
		
		private ByteBuffer buffer;

		public WorkTask(final int id, final SocketChannel channel, final ByteBuffer buffer) {
			this.id = id;
			this.channel = channel;
			this.buffer = buffer;
		}

		public void run() {
			try {
				Thread.sleep(3000);
				log.debug(Thread.currentThread().getName() + "-background operation task id - " + id);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			WriteTask writeTask = new WriteTask(id,channel,buffer);
			writeTaskQueue.offer(writeTask);
		}
		
		public ByteBuffer getBuffer() {
			return buffer;
		}

		public int getId() {
			return id;
		}		
	}	
}
