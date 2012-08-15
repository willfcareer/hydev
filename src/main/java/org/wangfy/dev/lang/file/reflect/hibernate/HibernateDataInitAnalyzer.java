package org.wangfy.dev.lang.file.reflect.hibernate;

/**
 * @author wangfuye
 * @Implemention: read interface method and write to file
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.wangfy.dev.lang.file.reflect.ClassAnalyzer;
import org.wangfy.dev.lang.log.ILog;
import org.wangfy.dev.lang.log.impl.LogFactory;


public class HibernateDataInitAnalyzer implements ClassAnalyzer {
	protected final ILog logger = LogFactory.getLog(this.getClass());

	private String interfaceFullName;

	private String allInterfaceStr;

	private String fileName;

	private boolean signed = false;

	/**
	 * 
	 */
	public Method[] getDeclaredMethods() {
		return this.getReflectedClass().getDeclaredMethods();
	}

	public Class getReflectedClass() {
		try {
			return Class.forName(this.interfaceFullName);
		} catch (ClassNotFoundException e) {
			logger.fatal("ClassNotFound:" + this.interfaceFullName
					+ " Program exit!");
			System.exit(0);
			return null;
		}
	}

	public StringBuffer getSrcFormatClass() {
		// TODO 自动生成方法存根
		return null;
	}

	public StringBuffer getStrFormatDeclaredMethod(Method mi) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(mi.getDeclaringClass().getName()).append(".").append(
				mi.getName()).append("(");
		int length = mi.getParameterTypes().length;
		Class[] clazz = mi.getParameterTypes();
		for (int i = 0; i < length; i++) {
			buffer.append(clazz[i].getSimpleName());
			if (i < length - 1)
				buffer.append(",");
		}
		buffer.append(")");
		return buffer;
	}

	public List<String> getStrFormatDeclaredMethods() {
		List<String> result = new ArrayList<String>();
		Method[] methods = this.getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			Method methVal = methods[i];
			StringBuffer buffer = this.getStrFormatDeclaredMethod(methVal);
			result.add(buffer.toString());
		}
		return result;
	}

	public void writeFormatDeclaredMethods(List result) {
		StringBuffer buffer = new StringBuffer();
		try {
			File file = new File(this.getFileName());
			if (file.exists() && !this.signed) {
				file.delete();
				this.signed = true;
			}
			for (int i = 0; i < result.size(); i++) {
				buffer.append("<row>").append("\n").append("   ").append(
						"<value>");
				String methodInfo = (String) result.get(i);
				buffer.append(methodInfo);
				buffer.append("</value>").append("\n").append("</row>").append(
						"\n");
			}
			logger.debug("buffer content:\n" + buffer);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file), "utf-8"));
			writer.append(buffer.toString());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String[] splitInterfaceNames() {
		String[] strInterfaces = this.allInterfaceStr.split(",");
		return strInterfaces;
	}

	public void classesOperate() {
		String[] strInterfaces = this.splitInterfaceNames();
		for (int i = 0; i < strInterfaces.length; i++) {
			this.interfaceFullName = strInterfaces[i];
			List result = this.getStrFormatDeclaredMethods();
			this.writeFormatDeclaredMethods(result);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		HibernateDataInitAnalyzer initAnalyzer = new HibernateDataInitAnalyzer();
		initAnalyzer
				.addInterfaceFullName("com.wangfy.util.file.reflect.ClassAnalyzer");
		initAnalyzer
				.addInterfaceFullName("com.wangfy.util.file.SmartAccessFile");
		initAnalyzer.setFileName("init.xml");
		initAnalyzer.classesOperate();
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getInterfaceFullName() {
		return interfaceFullName;
	}

	public void setInterfaceFullName(String interfaceFullName) {
		this.interfaceFullName = interfaceFullName;
	}

	public void addInterfaceFullName(String fullInterfaceName) {
		if (this.allInterfaceStr == null)
			this.allInterfaceStr = fullInterfaceName;
		else {
			this.allInterfaceStr += ",";
			this.allInterfaceStr += fullInterfaceName;
		}
	}

	public void setAllInterfaceStr(String allInterfaceStr) {
		this.allInterfaceStr = allInterfaceStr;
	}

}
