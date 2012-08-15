package org.wangfy.dev.network.threadpool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	
	private String server = "localhost";
	
	private int port = 8082;
	
	private int num = 1;
	
	public void connect(){
		for( int i = 0; i < num; i++){
			ClientThread thread = new ClientThread();
			thread.setTaskId(i);
			thread.setSrvport(port);
			thread.setSrvAddr(server);
			thread.start();
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Client client = new Client();
		client.connect();
	}

}

class ClientThread extends Thread{	
	
	private Socket socket;
	
	private int srvport = 8082;
	
	private String srvAddr = "localhost";
	
	private int taskId;	
	
	public void run() {
		// TODO Auto-generated method stub
		try {
			socket = new Socket( InetAddress.getByName(srvAddr),srvport);
			BufferedReader reader = this.getReader(socket);
			PrintWriter writer = this.getWriter(socket);
			writer.write(String.valueOf(taskId) + "testinfo");
			writer.flush();
			System.out.println(reader.readLine());
			if(socket != null)
				socket.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private BufferedReader getReader(Socket socket) throws IOException {
		InputStream in = socket.getInputStream();
		return new BufferedReader(new InputStreamReader(in));
	}
	private PrintWriter getWriter(Socket socket) throws IOException{
		OutputStream out = socket.getOutputStream();
		return new PrintWriter(new OutputStreamWriter(out));
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public int getSrvport() {
		return srvport;
	}

	public void setSrvport(int srvport) {
		this.srvport = srvport;
	}

	public String getSrvAddr() {
		return srvAddr;
	}

	public void setSrvAddr(String srvAddr) {
		this.srvAddr = srvAddr;
	}


	
}