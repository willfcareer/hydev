package org.wangfy.dev.network.nio;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StandardServerBootstrap {

	private final Log log = LogFactory.getLog(StandardServerBootstrap.class);
	
	public void start() throws IOException{
		Executor receiverExecutor = Executors.newCachedThreadPool();
		Executor workerExecutor = Executors.newCachedThreadPool();			
		StandardRequestReceiver receiver = new StandardRequestReceiver();
		receiver.setExecutor(receiverExecutor);
		StandardRequestManager manager = new StandardRequestManager(workerExecutor);
		receiver.setRequestManager(manager);		
		receiver.startup();
		log.info("Request Receiver startup finished");
		manager.startup();
		log.info("Request Manager startup finished");
		log.info("System startup finnished");		
	}
	
	public static void main(String[] args) throws IOException {	
		new StandardServerBootstrap().start();
	}
}
