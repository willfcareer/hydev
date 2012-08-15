package org.wangfy.dev.test;

public class Main {

	public static void main(String[] args) {
		String s0 = "123";
		String s1 = "123";
		String s2 = new String("123");
		String s22 = new String("123");
		String s3 = s2;
		String s4 = s3.intern();
		System.out.printf("%s,%s,%s,%s,%s,%s", s0 == s1, s1 == s2,
				s1.intern() == s2.intern(), s2 == s22, s2 == s3, s3 == s4);
	}

}