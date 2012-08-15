package org.wangfy.dev.lang.log;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class AbstractLog implements ILog, Serializable {

	protected abstract void log(int level, Object message, Throwable t);

	protected abstract void updateParamMap(int level, Object message);

	protected abstract String getLayout(String strLayout);

	protected abstract void write(String... strings);	

	protected String getFormatDate() {
		return new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss").format(new Date());
	}

	/*
	 * 获取异常堆栈的字符串格式
	 */
	protected String getStrEx(Throwable t) {
		if (t != null) {
			StringWriter sw = new StringWriter();
			t.printStackTrace(new PrintWriter(sw));
			String strExStack = sw.toString();
			return strExStack;
		} else
			return null;
	}

	public static void inspect(Object message) {		
		System.err.println("[INSPECT]|====>" + message);
	}

	public static void printlnArray(Object message) {
		System.out.println("[ArrayElementType]|====>"
				+ message.getClass().getName());
	}
}
