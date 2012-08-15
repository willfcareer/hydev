package org.wangfy.dev.classloader.example;

import java.io.IOException;
import java.net.URL;

public class MyClassLoader {

	public void showResourcePath() {
		System.out.println(System.getProperty("sun.boot.class.path"));
		URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
		for (int i = 0; i < urls.length; i++) {
			System.out.println(urls[i].toExternalForm());
		}
		System.out.println(System.getProperty("java.ext.dirs"));

		// this method can't return values of CLASSPATH configured by windows
		// environment variable in eclipse environment
		// you can run this sentence in cmd
		System.out.println(System.getProperty("java.class.path"));

		System.out.println(sun.misc.Launcher.getLauncher().getClass().getClassLoader());

		System.out.println(ClassLoader.getSystemResource("java/lang/String.class"));
		System.out.println(this.getClass().getResource("MyClassLoader.class"));
		System.out.println(this.getClass().getResource("/org/wangfy/dev/classloader/example/MyClassLoader.class"));

		System.out.println(ClassLoader.getSystemResource("org/wangfy/dev/classloader/example/MyClassLoader.class"));
		System.out.println(Thread.currentThread().getContextClassLoader().getResource("MyClassLoader.class"));
		System.out.println(Thread.currentThread().getContextClassLoader().getSystemResource("org/wangfy/dev/classloader/example/MyClassLoader.class"));
	}

	public void showClassLoader() {
		ClassLoader systemClassloader = ClassLoader.getSystemClassLoader();
		ClassLoader extensionClassloader = systemClassloader.getParent();
		ClassLoader bootstrapClassLoader = extensionClassloader.getParent();

		System.out.println(systemClassloader == null ? "null" : systemClassloader.getClass().getName());
		System.out.println(extensionClassloader == null ? "null" : extensionClassloader.getClass().getName());
		System.out.println(bootstrapClassLoader == null ? "null" : bootstrapClassLoader.getClass().getName());

		// rt.jar包中的类
		ClassLoader cl = System.class.getClassLoader();
		System.out.println(cl == null ? "null" : cl.getClass().getName());
		// rt.jar包中的类
		cl = javax.swing.Box.class.getClassLoader();
		System.out.println(cl == null ? "null" : cl.getClass().getName());

		// DNSNameService为jdk1.6.0_02\jre\lib\ext目录下dnsns.jar中的类
		cl = sun.net.spi.nameservice.dns.DNSNameService.class.getClassLoader();
		System.out.println("ext目录下dnsns jar包中中的类" + cl == null ? "null" : cl.getClass().getName());

	}

	public void test() {
		String p0 = Class.class.getResource("/").getPath();
		System.out.println("p0 = " + p0);
		URL u1 = getClass().getResource("");
		System.out.println("u1 = " + u1);
		URL u2 = Class.class.getResource("/com/cssnet/sword/cas/client/web/AgentListener.class");
		System.out.println("u2 = " + u2);

		URL uc1 = ClassLoader.getSystemResource("/");
		URL uc2 = ClassLoader.getSystemResource("");

		URL uc3 = Class.class.getClassLoader().getSystemResource("java/lang/Object.class");
		System.out.println(uc3);
	}

	public static void main(String[] args) {
		MyClassLoader myLoader = new MyClassLoader();
		myLoader.showResourcePath();
		myLoader.showClassLoader();
		myLoader.test();
	}
}
