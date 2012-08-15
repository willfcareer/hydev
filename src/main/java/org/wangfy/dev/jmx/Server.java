package org.wangfy.dev.jmx;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.registry.LocateRegistry;
import java.util.HashMap;
import java.util.Set;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.remote.JMXAuthenticator;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import javax.security.auth.Subject;

public class Server {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @throws NotCompliantMBeanException 
	 * @throws MBeanRegistrationException 
	 * @throws InstanceAlreadyExistsException 
	 * @throws NullPointerException 
	 * @throws MalformedObjectNameException 
	 */
	public static void main(String[] args) throws MalformedURLException, IOException, InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException, MalformedObjectNameException, NullPointerException {
		// TODO Auto-generated method stub

		LocateRegistry.createRegistry(8877);
		MBeanServer server = MBeanServerFactory.createMBeanServer();
		ObjectName helloName = new ObjectName("hello:name=hello");
		HelloWorld hello = new HelloWorld();
		HashMap<String, Object> prop = new HashMap<String, Object>();
		prop.put(JMXConnectorServer.AUTHENTICATOR, new JMXAuthenticator() {

			public Subject authenticate(Object credentials) {
				System.out.println(credentials);
				if (credentials instanceof String) {
					if (credentials.equals("hello")) {
						return new Subject();
					}
				}
				throw new SecurityException("not authicated");
			}
		});
		JMXConnectorServer cserver = JMXConnectorServerFactory.newJMXConnectorServer(new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:8877/hello"), prop, server);
		cserver.start();
		server.registerMBean(hello, helloName);
		for (ObjectInstance object : server.queryMBeans(null, null)) {
			System.out.println(object.getObjectName());
		}
		System.out.println(hello);
		System.out.println("start.....");
	}
}
