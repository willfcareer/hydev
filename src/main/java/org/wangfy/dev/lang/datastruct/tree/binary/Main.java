package org.wangfy.dev.lang.datastruct.tree.binary;

/**
 * 
 * Main.java
 * 
 * @author fuyewang E-mail: willfcareer@sohu.com
 * @version 1.0 Creation Date：2009-12-6 下午03:20:29
 * @since 1.0
 * 
 */
public class Main
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		BinaryNode<String> a = new BinaryNode<String>("A");
		BinaryNode<String> b = new BinaryNode<String>("B");
		BinaryNode<String> c = new BinaryNode<String>("C");
		BinaryNode<String> d = new BinaryNode<String>("D");
		BinaryNode<String> e = new BinaryNode<String>("E");

		a.addLeftChild(c);
		a.addRightChild(b);

		c.addLeftChild(d);
		d.addRightChild(e);

		a.preorderTraversal(a);
		System.out.println();
		a.inorderTraversal(a);
		System.out.println();
		a.postorderTraversal(a);
	}

}
