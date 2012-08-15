package org.wangfy.dev.network.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Client {

	private final Log logger = LogFactory.getLog(this.getClass());

	private Socket socket;

	private int srvPort = 8082;
	
	private int port = 9092;

	public void connect() {
		this.socket = new Socket();
		try {
			this.socket.setSoLinger(true, 3000);			
			this.socket.setReuseAddress(true);
			
			SocketAddress localAddr = new InetSocketAddress("localhost", port);
			SocketAddress remoteAddr = new InetSocketAddress("localhost", srvPort);
			socket.bind(localAddr);
			socket.connect(remoteAddr);
			this.logger.info("Connect to "+ remoteAddr);
			InputStream in = socket.getInputStream();
			OutputStream out = socket.getOutputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
			this.logger.info("Start send data . . .");
			for (int i = 0; i < 10; i++) {
				writer.write(i+" Hello, server!\n");
			}
			writer.flush();
			socket.shutdownOutput();
			
			String data;
			while ((data = reader.readLine()) != null) {
				this.logger.info(data);
			}
			this.logger.info("Socket close . . .");
			if (socket != null)
				socket.close();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Start client . . .");
		Client client = new Client();
		client.connect();
		System.out.println("Client success to exit");		
	}

}
