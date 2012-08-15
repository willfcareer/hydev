package org.wangfy.dev.sync;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class Config {

	/*
	 * CHAR
	 */
	public static char TB = '\t';

	public static char CR = '\r';
	public static char LF = '\n';

	public static String CRLF = "\r\n";

	/*
	 * CMD
	 */
	public static String START = "START";
	public static String END = "END";
	public static String FNAME = "FNAME";
	public static String PATH = "PATH";
	public static String SIZE = "SIZE";

	public String cfg = "sync.properties";

	public String KEY_SERVER_ADDR = "server.addr";
	public String KEY_SERVER_PORT = "server.port";
	public String KEY_SERVER_PATH = "server.path";

	public String KEY_CLIENT_ADDR = "client.addr";
	public String KEY_CLIENT_PORT = "client.port";
	public String KEY_CLIENT_PATH = "client.path";

	private Properties properties = new Properties();

	private static class StaticNestedClass {
		private static Config instance = new Config();
	}

	private Config() {
		URL url = Config.class.getResource("");
		File file = new File(new File(url.getFile()), cfg);

		try {
			properties.load(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public static Config getInstance() {
		return StaticNestedClass.instance;
	}

	public String getServerAddr() {
		return properties.getProperty(KEY_SERVER_ADDR);
	}

	public int getServerPort() {
		return Integer.valueOf(properties.getProperty(KEY_SERVER_PORT));
	}

	public String getServerPath() {
		return properties.getProperty(KEY_SERVER_PATH);
	}

	public String getClientAddr() {
		return properties.getProperty(KEY_CLIENT_ADDR);
	}

	public int getClientPort() {
		return Integer.valueOf(properties.getProperty(KEY_CLIENT_PORT));
	}

	public String getClientPath() {
		return properties.getProperty(KEY_CLIENT_PATH);
	}

	public static void main(String[] args) {
		Config config = Config.getInstance();
		System.out.println(config.getServerAddr());
		System.out.println(config.getClientPort());
	}

}
