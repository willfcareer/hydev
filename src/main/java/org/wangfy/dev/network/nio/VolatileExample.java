package org.wangfy.dev.network.nio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VolatileExample {

	private volatile List list = new ArrayList();
	
	public void addObject(Object o){
		this.list.add(o);
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		VolatileExample v1 = new VolatileExample();
		v1.addObject(new Integer(1));
		
		VolatileExample v2 = new VolatileExample();		
		v2.addObject(new Integer(2));
	}

}
