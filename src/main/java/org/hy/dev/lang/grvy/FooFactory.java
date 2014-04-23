package org.hy.dev.lang.grvy;

public class FooFactory {

	public static Foo getInstance(){
		return new FooImpl();
	}
}
