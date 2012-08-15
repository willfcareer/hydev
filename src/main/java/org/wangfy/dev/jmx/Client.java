package org.wangfy.dev.jmx;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.management.JMX;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class Client {

	static HelloWorldMBean hello;

	public static void main(String[] args) throws Exception {
		final AtomicInteger count = new AtomicInteger();
		HashMap<String, Object> prop = new HashMap<String, Object>();
		prop.put(JMXConnector.CREDENTIALS, "hello");
		final JMXConnector conn = JMXConnectorFactory.connect(new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:8877/hello"), prop);
		conn.connect();
		Runnable r = new Runnable() {

			public void run() {
				for (int i = 0; i < 10; i++) {
					try {
						final HelloWorldMBean hello = JMX.newMBeanProxy(conn.getMBeanServerConnection(), new ObjectName("hello:name=hello"), HelloWorldMBean.class);
						hello.setName("bearice");
						hello.sayHello();
						hello.getHelloString();
						System.out.println(count.incrementAndGet());
						Client.hello = hello;
						System.out.println(hello.equals(hello.getThis()));
					} catch (Exception ex) {
						Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
					}
				}
			}
		};
		Thread[] ts = new Thread[5];
		for (int i = 0; i < ts.length; i++) {
			ts[i] = new Thread(r);
			ts[i].start();
		}
		for (Thread t : ts) {
			t.join();
		}
		System.out.println(hello.equals(hello.getThis()));
		System.out.println(hello.getId());
		System.out.println(hello);
	}
}
