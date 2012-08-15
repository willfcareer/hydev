package com.hy.util.io;

import java.io.IOException;

import com.hy.util.io.SockIOPool.SockIO;

public class SockIOPoolTest {

	public static void main(String[] args) throws IOException {
		SockIOPool pool = SockIOPool.getInstance();
		pool.setServers(new String[] { "localhost:8082" });
		pool.initialize();
		String key = "key";
		for (int i = 0; i < 10; i++) {
			SockIO sockIO = pool.getSock(key);
			String message = key + i + "\r\n";
			sockIO.write(message.getBytes());
			sockIO.flush();
			System.out.println("Send message - " + message);
			String result = sockIO.readLine();
			System.out.println("Receive message - " + result);
			sockIO.close();
		}
	}

}
