package org.wangfy.dev.sync;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class SyncServer {

	private ServerSocket srvSocket;

	private Config config = Config.getInstance();

	public void init() throws IOException {
		srvSocket = new ServerSocket(config.getServerPort());
		srvSocket.setReuseAddress(true);
	}

	public void accept() throws IOException {
		Socket socket = srvSocket.accept();
		InputStream is = socket.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);

		br.readLine();
		String line = br.readLine();
		String name = getValue(line);
		line = br.readLine();
		String path = getValue(line);
		line = br.readLine();
		int size = Integer.valueOf(getValue(line));
		writeFile(path + name, size, is);
		line = br.readLine();
		System.out.println(line);
	}

	public void writeFile(String fileName, int size, InputStream is) throws IOException {
		File file = new File(config.getServerPath() + fileName);
		if (file.exists())
			file.delete();
		FileOutputStream fos = new FileOutputStream(file);
		byte[] bytes = new byte[1024];
		int len = is.read(bytes);
		fos.write(bytes);
		while (len < size) {
			int l = is.read(bytes);
			len = len + l;
			fos.write(bytes, 0, l);
		}
		fos.flush();
		fos.close();
	}

	public String getValue(String line) {
		return line.substring(line.indexOf(Config.TB) + 1);
	}

	public static void main(String[] args) throws IOException {
		SyncServer server = new SyncServer();
		server.init();
		server.accept();
	}
}
