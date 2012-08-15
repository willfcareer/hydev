package org.wangfy.dev.network.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {

	public static int num = 1;
	public static int size = 1024;
	public static InetSocketAddress srvaddr = new InetSocketAddress("localhost", 8082);
	private static Charset charset = Charset.forName("GBK");

	public static class Task implements Runnable {
		protected int index;

		public Task(int index) {
			this.index = index;
		}

		public void run() {
			try {
				SocketChannel sckchannel = SocketChannel.open();
				sckchannel.configureBlocking(false);
				Selector selector = Selector.open();
				sckchannel.register(selector, SelectionKey.OP_CONNECT);
				sckchannel.connect(srvaddr);
				ByteBuffer buffer = ByteBuffer.allocate(size);
				while (true) {
					int n = selector.select();
					if (n <= 0)
						return;
					Iterator it = selector.selectedKeys().iterator();
					while (it.hasNext()) {
						SelectionKey key = (SelectionKey) it.next();
						it.remove();
						if (key.isConnectable()) {
							SocketChannel channel = (SocketChannel) key.channel();
							if (channel.isConnectionPending())
								channel.finishConnect();
							channel.write(Client.encode("Hello, wangfy11! - " + index + "\r\n"));
							channel.register(selector, SelectionKey.OP_READ);
						} else if (key.isReadable()) {
							SocketChannel channel = (SocketChannel) key.channel();
							int count = channel.read(buffer);
							if (count > 0)
								System.out.println(buffer);
							else {
								break;
							}
							channel.close();
						}
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static String decode(ByteBuffer buffer) {
		CharBuffer charbuffer = charset.decode(buffer);
		return charbuffer.toString();
	}

	public static ByteBuffer encode(String str) {
		return charset.encode(str);
	}

	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(num);
		for (int i = 0; i < num; i++) {
			executor.execute(new Task(i));
		}
		executor.shutdown();

	}
}
