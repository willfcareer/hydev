package org.wangfy.dev.lang.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EnumMap;
import java.util.Set;



public class Log {
	public static final int LEVEL_FATAL = 1;

	public static final int LEVEL_ERROR = 2;

	public static final int LEVEL_WARN = 3;

	public static final int LEVEL_INFO = 4;

	public static final int LEVEL_DEBUG = 5;

	public static final int LEVEL_TRACE = 6;

	public static enum Param {
		LEV("%p"), DATE("%d"), MAIN("%t"), FCNAME("%fc"), SCNAME("%sc"), METHOD(
				"%M"), MSG("%m"), LINENUM("%L"), NEWLINE("%n");
		public String format;

		Param(String format) {
			this.format = format;
		}
	}

	public static boolean staticWrite = true;

	public static boolean staticOff = false;

	public static int staticLevel = 5;

	public static String staticCharset = "";

	public static String staticStdoutLayout = "";

	protected EnumMap<Param, String> paramMap = new EnumMap<Param, String>(
			Param.class);

	protected String stdoutLayout = "[%p]%t%sc.%M(%L)|====> %m%n";// 默认控制台打印格式

	protected String fileRLayout = "%d[%p]%t%fc.%M(%L)|====> %m";// 默认文件记录格式

	protected Class clazz;

	protected boolean write = true;

	protected boolean off = false;

	protected String logFile = "Log.log";// 日志文件,默认为当前目录下Log.log

	protected long fileLen = 2 * 1024 * 1024;// 日志文件的默认大小,单位为字节

	protected int curtLevel = 5;// 默认级别设置为5,即Debug级

	protected String charset = "utf-8";

	public static Log getLog(Class clazz) {
		return new Log(clazz);
	}

	protected Log(Class clazz) {
		this.setClazz(clazz);
	}

	protected void log(int level, Object message, Throwable t) {
		if (!Log.staticOff && !this.off && Log.staticLevel >= level
				&& this.curtLevel >= level) {
			this.updateParamMap(level, message);
			String strEx = this.getStrEx(t);
			if (Log.staticStdoutLayout.equals(""))
				System.out.print(this.getLayout(this.stdoutLayout));
			else
				System.out.print(this.getLayout(Log.staticStdoutLayout));
			if (strEx != null)
				System.err.println(strEx);
			if (Log.staticWrite && this.write)
				this.write(this.getLayout(this.fileRLayout), strEx);
			this.paramMap.clear();
		}
	}

	protected String getStrLevel(int level) {
		String strLevel = null;
		switch (level) {
		case Log.LEVEL_INFO:
			strLevel = "INFO";
			break;
		case Log.LEVEL_WARN:
			strLevel = "WARN";
			break;
		case Log.LEVEL_ERROR:
			strLevel = "ERROR";
			break;
		case Log.LEVEL_FATAL:
			strLevel = "FATAL";
			break;
		case Log.LEVEL_TRACE:
			strLevel = "TRACE";
			break;
		default:
			strLevel = "DEBUG";
		}
		return strLevel;
	}


	protected void updateParamMap(int level, Object message) {
		Throwable thable = new Throwable();
		StackTraceElement[] ste = thable.getStackTrace();
		for (int i = 0; i < ste.length; i++) {
			if (this.clazz.getName().equals(ste[i].getClassName())) {
				if (!this.paramMap.containsKey(Log.Param.LINENUM))
					this.paramMap.put(Log.Param.LINENUM, String.valueOf(ste[i]
							.getLineNumber()));

				String methodName = ste[i].getMethodName();
				if (methodName.equals("main")) {
					this.paramMap.put(Log.Param.MAIN, "[main]");
				} else {
					if (!this.paramMap.containsKey(Log.Param.METHOD))
						this.paramMap.put(Log.Param.METHOD, methodName);
				}
			}
		}
		if (!this.paramMap.containsKey(Log.Param.MAIN))
			this.paramMap.put(Log.Param.MAIN, "");
		if (!this.paramMap.containsKey(Log.Param.METHOD))
			this.paramMap.put(Log.Param.METHOD, "main");
		this.paramMap.put(Log.Param.LEV, this.getStrLevel(level));
		this.paramMap.put(Log.Param.DATE, this.getFormatDate());
		this.paramMap.put(Log.Param.FCNAME, this.clazz.getName());
		this.paramMap.put(Log.Param.SCNAME, this.clazz.getSimpleName());
		this.paramMap.put(Log.Param.NEWLINE, "\n");
		if (message != null)
			this.paramMap.put(Log.Param.MSG, message.toString());
		else
			this.paramMap.put(Log.Param.MSG, "null");
	}

	protected String getLayout(String strLayout) {
		Set<Param> params = this.paramMap.keySet();
		for (Param p : params) {
			strLayout = strLayout.replaceAll(p.format, this.paramMap.get(p));
		}
		return strLayout;
	}

	protected void write(String... strings) {
		try {
			File file = new File(this.logFile);
			if (file.exists()) {
				if (file.length() > this.fileLen)
					file.delete();
			}
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file, true), this.charset));
			for (String s : strings) {
				if (s != null) {
					writer.append(s);
					writer.newLine();
				}
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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

	/*
	 * public methods
	 */

	public void trace(Object message) {
		this.log(Log.LEVEL_TRACE, message, null);
	}

	public void debug(Object message) {
		this.log(Log.LEVEL_DEBUG, message, null);
	}

	public void info(Object message) {
		this.log(Log.LEVEL_INFO, message, null);
	}

	public void warn(Object message) {
		this.log(Log.LEVEL_WARN, message, null);
	}

	public void error(Object message) {
		this.log(Log.LEVEL_ERROR, message, null);
	}

	public void fatal(Object message) {
		this.log(Log.LEVEL_FATAL, message, null);
	}

	public void trace(Object message, Throwable t) {
		this.log(Log.LEVEL_TRACE, message, t);
	}

	public void debug(Object message, Throwable t) {
		this.log(Log.LEVEL_DEBUG, message, t);
	}

	public void error(Object message, Throwable t) {
		this.log(Log.LEVEL_ERROR, message, t);
	}

	public void fatal(Object message, Throwable t) {
		this.log(Log.LEVEL_FATAL, message, t);
	}

	public void info(Object message, Throwable t) {
		this.log(Log.LEVEL_INFO, message, t);
	}

	public void warn(Object message, Throwable t) {
		this.log(Log.LEVEL_WARN, message, t);
	}

	/*
	 * 存取器
	 */
	public Class getClazz() {
		return clazz;
	}

	public void setClazz(Class clazz) {
		this.clazz = clazz;
	}

	public int getCurtLevel() {
		return curtLevel;
	}

	public void setCurtLevel(int curtLevel) {
		this.curtLevel = curtLevel;
	}

	public long getFileLen() {
		return fileLen;
	}

	public void setFileLen(long fileLen) {
		this.fileLen = fileLen;
	}

	public String getFileRLayout() {
		return fileRLayout;
	}

	public void setFileRLayout(String fileRLayout) {
		this.fileRLayout = fileRLayout;
	}

	public String getLogFile() {
		return logFile;
	}

	public void setLogFile(String logFile) {
		this.logFile = logFile;
	}

	public boolean isOff() {
		return off;
	}

	public void setOff(boolean off) {
		this.off = off;
	}

	public String getStdoutLayout() {
		return stdoutLayout;
	}

	public void setStdoutLayout(String stdoutLayout) {
		this.stdoutLayout = stdoutLayout;
	}

	public boolean isWrite() {
		return write;
	}

	public void setWrite(boolean write) {
		this.write = write;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public static String getStaticCharset() {
		return staticCharset;
	}

	public static void setStaticCharset(String staticCharset) {
		Log.staticCharset = staticCharset;
	}

	public static int getStaticLevel() {
		return staticLevel;
	}

	public static void setStaticLevel(int staticLevel) {
		Log.staticLevel = staticLevel;
	}

	public static boolean isStaticOff() {
		return staticOff;
	}

	public static void setStaticOff(boolean staticOff) {
		Log.staticOff = staticOff;
	}

	public static String getStaticStdoutLayout() {
		return staticStdoutLayout;
	}

	public static void setStaticStdoutLayout(String staticStdoutLayout) {
		Log.staticStdoutLayout = staticStdoutLayout;
	}

	public static boolean isStaticWrite() {
		return staticWrite;
	}

	public static void setStaticWrite(boolean staticWrite) {
		Log.staticWrite = staticWrite;
	}

}
