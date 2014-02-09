package org.hy.dev.zk.grvy;

public class FooFactory {

	public static Foo getInstance(){
		return new FooImpl();
	}
}
