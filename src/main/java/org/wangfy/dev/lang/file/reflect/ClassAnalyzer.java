package org.wangfy.dev.lang.file.reflect;

import java.lang.reflect.Method;
import java.util.List;

public interface ClassAnalyzer {
	
	public Class getReflectedClass();
	
	public Method[] getDeclaredMethods();
	
	public List<String> getStrFormatDeclaredMethods();
	
	public StringBuffer getStrFormatDeclaredMethod(Method mi);
	
	public void writeFormatDeclaredMethods(List result);
	
	public StringBuffer getSrcFormatClass();
	
	public void classesOperate();
}
