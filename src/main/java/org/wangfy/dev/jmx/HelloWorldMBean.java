package org.wangfy.dev.jmx;

import java.io.Serializable;

public interface HelloWorldMBean extends Serializable {
	void setName(String name);

	String getName();

	void sayHello();

	String getHelloString();

	int getId();

	HelloWorldMBean getThis();
}
