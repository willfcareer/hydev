package org.wangfy.dev.network.threadpool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

	private int port = 8082;
	
	private ServerSocket serverSocket;
	
	private ExecutorService executorService;
	
	private final int POOL_SIZE = 1;
	
	public Server() throws IOException{
		serverSocket = new ServerSocket(port);
		int n = Runtime.getRuntime().availableProcessors();
		executorService = Executors.newFixedThreadPool(n * POOL_SIZE);
		System.out.println("Server start");
	}
	
	public void service(){
		while(true){
			Socket socket;
			try{
				socket = serverSocket.accept();
				executorService.execute(new Handler(socket));
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Server server = new Server();
		server.service();
	}

}
class Handler implements Runnable{
	
	private Socket socket;

	public Handler(Socket socket){
		this.socket = socket;
	}
	
	private PrintWriter getWriter(Socket socket) throws IOException{
		OutputStream out = socket.getOutputStream();
		return new PrintWriter(new OutputStreamWriter(out));
	}
	
	private BufferedReader getReader(Socket socket) throws IOException{
		InputStream in = socket.getInputStream();
		return new BufferedReader( new InputStreamReader(in));
	}
	
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("New connection accepted" + socket.getInetAddress() + ":" + socket.getPort());
		try {
			BufferedReader reader = this.getReader(socket);
			PrintWriter writer = this.getWriter(socket);
			
			String id;
			while(( id = reader.readLine()) != null){
				System.out.println("Task " + id + ": start in thread ");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {}
				System.out.println("Task " + id + ": end");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(socket != null)
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	
}
