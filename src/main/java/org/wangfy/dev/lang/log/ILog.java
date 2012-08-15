package org.wangfy.dev.lang.log;

public interface ILog {

	// Basic Business
	public void trace(Object message);

	public void debug(Object message);

	public void info(Object message);

	public void warn(Object message);

	public void error(Object message);

	public void fatal(Object message);

	public void trace(Object message, Throwable t);

	public void debug(Object message, Throwable t);

	public void error(Object message, Throwable t);

	public void fatal(Object message, Throwable t);

	public void info(Object message, Throwable t);

	public void warn(Object message, Throwable t);


	/*
	 * Configure Part
	 */
	public Class getClazz();

	public void setClazz(Class clazz);

	public int getCurtLevel();

	public void setCurtLevel(int curtLevel);

	public long getFileLen();

	public void setFileLen(long fileLen);

	public String getFileRLayout();

	public void setFileRLayout(String fileRLayout);

	public String getLogFile();

	public void setLogFile(String logFile);

	public boolean isOff();

	public void setOff(boolean off);

	public String getStdoutLayout();

	public void setStdoutLayout(String stdoutLayout);

	public boolean isWrite();

	public void setWrite(boolean write);

	public String getCharset();

	public void setCharset(String charset);

}
