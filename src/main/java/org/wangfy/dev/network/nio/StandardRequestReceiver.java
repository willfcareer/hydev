package org.wangfy.dev.network.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Executor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StandardRequestReceiver implements Runnable {
	
	private final Log log = LogFactory.getLog(this.getClass());

	private int port = 8082;

	private ServerSocketChannel srvChannel;
	
	private StandardRequestManager requestManager;
	
	private Executor executor;

	public StandardRequestReceiver() throws IOException {
		this.srvChannel = ServerSocketChannel.open();
		this.srvChannel.socket().setReuseAddress(true);
		this.srvChannel.socket().bind(new InetSocketAddress(port));
		log.info(Thread.currentThread().getName() + "- open channel and bind in " + port);
	}

	public void startup(){
		this.executor.execute(this);
	}
	public void run() {
		int num = 0;
		for (;;) {
			try {
				SocketChannel channel = this.srvChannel.accept();
				log.debug(Thread.currentThread().getName() + "- accepted request [" + (++num) + "]");
				requestManager.register(num, channel);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void setRequestManager(StandardRequestManager requestManager) {
		this.requestManager = requestManager;
	}

	public void setExecutor(Executor executor) {
		this.executor = executor;
	}
	
}
