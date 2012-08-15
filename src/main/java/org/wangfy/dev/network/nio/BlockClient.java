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

public class BlockClient {

	private SocketChannel sckchannel;

	public BlockClient() throws IOException {
		sckchannel = SocketChannel.open();
		InetAddress addr = InetAddress.getLocalHost();
		InetSocketAddress sckAddr = new InetSocketAddress(addr, 8082);
		sckchannel.connect(sckAddr);
		System.out.println("Create connection to server success");
	}

	private PrintWriter getWriter(Socket socket) throws IOException {
		OutputStream out = socket.getOutputStream();
		return new PrintWriter(new OutputStreamWriter(out));
	}

	private BufferedReader getReader(Socket socket) throws IOException {
		InputStream in = socket.getInputStream();
		return new BufferedReader(new InputStreamReader(in));
	}

	public void talk() throws IOException {
		try {
			BufferedReader reader = this.getReader(sckchannel.socket());
			PrintWriter writer = this.getWriter(sckchannel.socket());
			BufferedReader localreader = new BufferedReader(new InputStreamReader(System.in));
			String msg;
			while ((msg = localreader.readLine()) != null) {
				writer.print(msg);
				writer.flush();
				System.out.println(reader.readLine());
				if (msg.equals("bye"))
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				sckchannel.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BlockClient client = new BlockClient();
		client.talk();
	}

}
