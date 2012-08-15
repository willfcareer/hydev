package org.wangfy.dev.network.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Server {

	private final Log logger = LogFactory.getLog(this.getClass());

	private ServerSocket serverSocket;

	private int port = 8082;

	public void listen() {
		try {
			serverSocket = new ServerSocket(port);		
			
			this.logger.info("Server started. Listen in " + serverSocket.getInetAddress().getHostAddress() + " :" + serverSocket.getLocalPort());	
			
			Socket socket = serverSocket.accept();
			
			socket.setReuseAddress(true);
			socket.setSoLinger(true, 3000);
			
			this.logger.info("Accept connection");
			InputStream in = socket.getInputStream();
			OutputStream out = socket.getOutputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
			String data;
			while ((data = reader.readLine()) != null) {
				this.logger.info(data);
				writer.write("I'm server!\n");
			}
			writer.flush();
			socket.shutdownOutput();
			if (socket != null)
				socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Start server . . .");
		Server server = new Server();
		server.listen();
		System.out.println("Server success to exit");
	}

}
