package org.wangfy.dev.network.nio;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



public class StandardRequestManager{
	
	private final Log log = LogFactory.getLog(this.getClass());
	
    private final AtomicInteger executorIndex = new AtomicInteger();
	
	private StandardExecutor[] stdExecutors;
	
	public StandardRequestManager(final Executor executor){
		int n = Runtime.getRuntime().availableProcessors();
		this.stdExecutors = new StandardExecutor[n];
		for(int i = 0; i < n; i++){
			stdExecutors[i] = new StandardExecutor(i,executor);
		}
		log.debug("create executor compareCount " + n);
	}

	StandardExecutor selectExecutor(){
        return stdExecutors[Math.abs(executorIndex.getAndIncrement() % stdExecutors.length)];
	}
	
	public void startup() throws IOException{
		for(int i = 0; i < stdExecutors.length; i++){
			stdExecutors[i].startup();
		}
	}

	public void register(final int id, final SocketChannel channel) throws IOException{
		this.configSocketChannel(channel);
		StandardExecutor stdExecutor = this.selectExecutor();
		stdExecutor.register(id, channel);
	}
	
	private void configSocketChannel(SocketChannel channel) throws IOException {
		channel.configureBlocking(false);
		channel.socket().setTcpNoDelay(true);
//		channel.socket().setSoLinger(true, 60 * 60);
	}
}


