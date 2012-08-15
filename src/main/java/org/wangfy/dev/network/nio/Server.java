package org.wangfy.dev.network.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Server {

	private final Log log = LogFactory.getLog(this.getClass());

	private int n = 0;

	private Selector selector;

	private ServerSocketChannel srvsckchannel;

	private int port = 8082;

	private Charset charset = Charset.forName("GBK");

	private Object gate = new Object();

	public Server() throws IOException {
		selector = Selector.open();
		srvsckchannel = ServerSocketChannel.open();
		srvsckchannel.socket().setReuseAddress(true);
		srvsckchannel.socket().bind(new InetSocketAddress(port));
		log.debug("Server start . . .");
	}

	public void accept() {
		for (;;) {
			try {
				SocketChannel sckchannel = srvsckchannel.accept();

				log.debug("Accept connection from " + sckchannel.socket().getInetAddress() + ":" + sckchannel.socket().getPort() + " total number = " + (++n));

				sckchannel.configureBlocking(false);
				ByteBuffer buffer = ByteBuffer.allocate(1024);

				synchronized (gate) {
					selector.wakeup();
					// notice channel.register method will override previous
					// register
					sckchannel.register(selector, SelectionKey.OP_READ, buffer);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void service() throws IOException {
		for (;;) {
			synchronized (gate) {
			}
			int n = selector.select();
			if (n == 0) continue;
			Set readykeys = selector.selectedKeys();
			Iterator it = readykeys.iterator();
			while (it.hasNext()) {
				SelectionKey key = null;
				try {
					key = (SelectionKey) it.next();
					it.remove();
					handle(key);
				} catch (IOException ioe) {
					ioe.printStackTrace();
					try {
						if (key != null) {
							key.cancel();
							key.channel().close();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public void handle(SelectionKey key) throws IOException {
		if (key.isReadable()) {
			receive(key);
		} else if (key.isWritable()) {
			send(key);
		}
	}

	public void send(SelectionKey key) throws IOException {
		ByteBuffer buffer = (ByteBuffer) key.attachment();
		SocketChannel sckchannel = (SocketChannel) key.channel();
		buffer.flip();
		String data = decode(buffer);
		if (data.indexOf("\r\n") == -1) return;
		String readata = data.substring(0, data.indexOf("\n") + 1);
		log.debug("read data : " + readata);
		String sendata = "echo : " + readata;
		log.debug("send data :" + sendata);
		ByteBuffer outputbuffer = encode(sendata);
		while (outputbuffer.hasRemaining())
			sckchannel.write(outputbuffer);
		// delete content that has been sent
		ByteBuffer tmpbuffer = encode(readata);
		buffer.position(tmpbuffer.limit());
		buffer.compact();

		sckchannel.register(key.selector(), SelectionKey.OP_READ, buffer);
		if (data.equals("bye")) {
			key.cancel();
			sckchannel.close();
			log.debug("Close connection to client");
		}
	}

	public void receive(SelectionKey key) throws IOException {
		ByteBuffer buffer = (ByteBuffer) key.attachment();
		SocketChannel sckchannel = (SocketChannel) key.channel();
		ByteBuffer readbuffer = ByteBuffer.allocate(512);
		int n = sckchannel.read(readbuffer);
		if (n <= 0) {
			sckchannel.close();
			return;
		}
		readbuffer.flip();
		buffer.limit(buffer.capacity());
		buffer.put(readbuffer);
		// register write event and read event repeatly
		sckchannel.register(key.selector(), SelectionKey.OP_WRITE, buffer);
	}

	public String decode(ByteBuffer buffer) {
		CharBuffer charbuffer = charset.decode(buffer);
		return charbuffer.toString();
	}

	public ByteBuffer encode(String str) {
		return charset.encode(str);
	}

	public static void main(String[] args) throws IOException {
		final Server server = new Server();
		Thread accepthread = new Thread() {
			public void run() {
				server.accept();
			}
		};
		accepthread.start();
		server.service();
	}

}
