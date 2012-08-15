package org.wangfy.dev.lang.log.impl;

import java.io.Serializable;

public class LogFactory implements Serializable {

	public static Log getLog(Class clazz) {
		return new Log(clazz);
	}
}
