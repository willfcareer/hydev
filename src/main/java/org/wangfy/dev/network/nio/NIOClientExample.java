package org.wangfy.dev.network.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class NIOClientExample implements Runnable {

	private final Log log = LogFactory.getLog(this.getClass());

	static int SIZE =100;

	private static AtomicInteger n = new AtomicInteger(0);

	private int id;

	private SocketChannel sckchannel;

	public NIOClientExample(int id) throws IOException {
		this.id = id;
		sckchannel = SocketChannel.open();
		InetAddress addr = InetAddress.getLocalHost();
//		InetAddress addr = InetAddress.getByName("192.168.92.130");		
		InetSocketAddress sckAddr = new InetSocketAddress(addr, 8082);
		sckchannel.socket().setReuseAddress(true);
		sckchannel.socket().setKeepAlive(true);
		sckchannel.connect(sckAddr);
	}

	private PrintWriter getWriter(Socket socket) throws IOException {
		OutputStream out = socket.getOutputStream();
		return new PrintWriter(new OutputStreamWriter(out));
	}

	private BufferedReader getReader(Socket socket) throws IOException {
		InputStream in = socket.getInputStream();
		return new BufferedReader(new InputStreamReader(in));
	}

	public void run() {
		try {
			BufferedReader reader = this.getReader(sckchannel.socket());
			PrintWriter writer = this.getWriter(sckchannel.socket());
			writer.write("Hello, server. I'm client - " + id + "\n");
			writer.flush();
			sckchannel.socket().shutdownOutput();
			log.debug(Thread.currentThread().getName() + " -finish send - " + id);
			String line = reader.readLine();
			log.debug(Thread.currentThread().getName() + " -finish receive - " + line);
			sckchannel.socket().shutdownInput();
		} catch (IOException e) {
			log.error(Thread.currentThread().getName() + " -exception happened when interact with server", e);
		} finally {
			try {
				sckchannel.close();
			} catch (IOException e) {
				log.error("exception happened when close channel", e);
			}
		}
	}

	public static void main(String[] args) throws IOException {
		ExecutorService exec = Executors.newFixedThreadPool(SIZE);
		for (int i = 1; i < 2; i++) {
			for (int index = 1; index <= SIZE; index++) {
				exec.execute(new NIOClientExample(index));
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		exec.shutdown();
	}
}
