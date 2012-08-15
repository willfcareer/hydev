package org.wangfy.dev.lang.file.reflect;

import java.lang.reflect.Method;
import java.util.List;

import org.wangfy.dev.lang.log.ILog;
import org.wangfy.dev.lang.log.impl.LogFactory;




public class SimpleClassAnalyzer implements ClassAnalyzer {

	public void ClassesOperate() {
		// TODO 自动生成方法存根

	}

	protected final ILog logger = LogFactory.getLog(this.getClass());

	private String fullClassName;

	private Class clazz;

	private Method[] methods;

	public void classesOperate() {
		// TODO 自动生成方法存根

	}

	public Method[] getDeclaredMethods() {
		return this.clazz.getDeclaredMethods();
	}

	public Class getReflectedClass() {
		try {
			return Class.forName(this.fullClassName);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public StringBuffer getSrcFormatClass() {
		// TODO 自动生成方法存根
		return null;
	}

	public StringBuffer getStrFormatDeclaredMethod(Method mi) {
		// TODO 自动生成方法存根
		return null;
	}

	public List<String> getStrFormatDeclaredMethods() {

		return null;
	}

	public void writeFormatDeclaredMethods(List result) {

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO 自动生成方法存根

	}

	public String getFullClassName() {
		return fullClassName;
	}

	public void setFullClassName(String fullClassName) {
		this.fullClassName = fullClassName;
	}

}
