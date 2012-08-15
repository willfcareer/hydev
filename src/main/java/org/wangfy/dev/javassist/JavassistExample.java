package org.wangfy.dev.javassist;

import java.io.IOException;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

/**
 * @author fuye.wangfy Dec 7, 2011 7:41:59 PM
 */
public class JavassistExample
{

	public static void main(String[] args) throws NotFoundException, CannotCompileException, InstantiationException, IllegalAccessException, IOException
	{
		ClassPool pool = ClassPool.getDefault();
		CtClass cc = pool.get("org.wangfy.dev.javassist.Bean");
		CtMethod cm = cc.getDeclaredMethod("doBiz");
		cm.insertBefore("{ System.out.println(\"before doBiz!\"); }");
		cm.insertAfter("{ System.out.println(\"after doBiz!\"); }");
		@SuppressWarnings("rawtypes")
		Class clazz = cc.toClass();
		Bean bean = (Bean) clazz.newInstance();
		bean.doBiz();
	}
}

class Bean
{
	public void doBiz()
	{
		System.out.println("doBizing!");
	}
}
