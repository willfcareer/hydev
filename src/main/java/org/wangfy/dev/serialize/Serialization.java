package org.wangfy.dev.serialize;

import java.io.Externalizable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class Serialization {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		File f = new File("serial.data");
		
		System.out.println(f.getAbsolutePath());
		OutputStream os = new FileOutputStream(f);
		ObjectOutputStream oos = new ObjectOutputStream(os);
		oos.writeObject(new Bean(1,"edwin"));
		oos.close();
		
		InputStream is = new FileInputStream(f);
		ObjectInputStream ois = new ObjectInputStream(is);
		Bean bean = (Bean) ois.readObject();
		System.out.println(bean.getName());
		ois.close();
	}
}

class Bean implements Externalizable {

	private static final long serialVersionUID = 1L;

	int id;
	String name;
	
	public Bean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Bean(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		// TODO Auto-generated method stub
		out.writeObject(name);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		// TODO Auto-generated method stub
		name = (String) in.readObject();
//		System.out.println(name);
	}
}
