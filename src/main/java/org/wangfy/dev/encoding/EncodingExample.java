package org.wangfy.dev.encoding;

import java.io.UnsupportedEncodingException;

public class EncodingExample {

	public void testCharacterEncoding() throws UnsupportedEncodingException{
		String zg = "中国";
		byte[] bytes = zg.getBytes("utf-8");
		
		// case 1不做任何处理，传递utf-8，容器当做iso-8859-1处理，浏览器默认gbk显示
		String zg1 = new String(bytes,"iso-8859-1");//容器当做iso-8859-1处理
		String zg2 = new String(zg1.getBytes("iso-8859-1"),"gbk");
		System.out.println(zg1);
		System.out.println(zg2);		
		System.out.println("===========================");
		//case 2 在程序中用utf-8解析，正确，然后容器把utf-8格式的字节流当成iso-8859-1输出，浏览器有gbk显示
		String zg3 = new String(bytes,"utf-8");
		String zg4 = new String(zg3.getBytes("iso-8859-1"),"gbk");
		System.out.println(zg3);
		System.out.println(zg4);	
		System.out.println("===========================");
		//case3 输出流变为utf-8格式
		String zg5 = new String(bytes,"iso-8859-1");
		byte[] bytes2 = zg5.getBytes("utf-8");
		String zg6 = new String(zg5.getBytes("utf-8"),"gbk");
		byte[] byte3 = zg6.getBytes("iso-8859-1");
		String zg7 = new String(zg5.getBytes("utf-8"),"utf-8");
		byte[] byte4 = zg7.getBytes("iso-8859-1");
		System.out.println(zg5);
		System.out.println(zg6);
		System.out.println(zg7);
		System.out.println("===========================");
		//case4 转换参数为utf-8格式
		String zg8 = new String(zg1.getBytes("iso-8859-1"),"utf-8");
		String zg9 = new String(zg8.getBytes("utf-8"),"gbk");
		String zg10 = new String(zg8.getBytes("utf-8"),"utf-8");
		System.out.println(zg8);
		System.out.println(zg9);
		System.out.println(zg10);		
		
	}
	public static void main(String[] args) throws UnsupportedEncodingException {
		EncodingExample encoding = new EncodingExample();		
		encoding.testCharacterEncoding();

	}

}
