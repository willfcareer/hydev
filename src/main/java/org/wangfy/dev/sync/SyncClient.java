package org.wangfy.dev.sync;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class SyncClient {

	private Config config = Config.getInstance();

	public void synchronize() throws IOException {
		Socket socket = new Socket(InetAddress.getByName(config.getServerAddr()), config.getServerPort());
		socket.setReuseAddress(true);
		OutputStream os = socket.getOutputStream();
		os.write(Config.START.getBytes());
		os.write(Config.LF);
		String fName = "MFP_1650_Driver_cn.exe";
		os.write(Config.FNAME.getBytes());
		os.write(Config.TB);
		os.write(fName.getBytes());
		os.write(Config.LF);
		os.write(Config.PATH.getBytes());
		os.write(Config.TB);
		os.write("/".getBytes());
		os.write(Config.LF);

		File file = new File(config.getClientPath() + fName);
		FileInputStream fis = new FileInputStream(file);
		os.write(Config.TB);
		int len = fis.available();
		os.write(String.valueOf(len).getBytes());
		os.write(Config.LF);

		byte[] bytes = new byte[1024];
		while ((len = fis.read(bytes)) != -1) {
			os.write(bytes, 0, len);			
		}
		os.write(Config.LF);
		os.write(Config.END.getBytes());
		os.write(Config.LF);
		os.flush();

		socket.close();
	}

	public static void main(String[] args) throws IOException {
		SyncClient client = new SyncClient();
		client.synchronize();
	}

}
