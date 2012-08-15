package org.wangfy.dev.lang.datastruct.tree.binary;

/**
 * 
 * BinaryNode.java
 * 
 * @author fuyewang E-mail: willfcareer@sohu.com
 * @version 1.0 Creation Date：2009-12-6 下午03:10:16
 * @since 1.0
 * 
 */
public class BinaryNode<Type>
{
	Type element;
	BinaryNode<Type> left;
	BinaryNode<Type> right;

	BinaryNode(Type element)
	{
		this(element, null, null);
	}

	BinaryNode(Type element, BinaryNode<Type> left, BinaryNode<Type> right)
	{
		this.element = element;
		this.left = left;
		this.right = right;
	}

	public void addLeftChild(BinaryNode<Type> left)
	{
		this.left = left;
	}

	public void addRightChild(BinaryNode<Type> right)
	{
		this.right = right;
	}

	public void preorderTraversal(BinaryNode<Type> binaryNode)
	{
		if (binaryNode == null)
			return;
		
		System.out.print(binaryNode.element);
		
		preorderTraversal(binaryNode.left);
		
		preorderTraversal(binaryNode.right);
	}
	
	public void inorderTraversal(BinaryNode<Type> binaryNode)
	{
		if (binaryNode == null)
			return;
		
		inorderTraversal(binaryNode.left);
		
		System.out.print(binaryNode.element);	
		
		inorderTraversal(binaryNode.right);
	}	
	
	public void postorderTraversal(BinaryNode<Type> binaryNode)
	{
		if (binaryNode == null)
			return;
		
		postorderTraversal(binaryNode.left);
		
		postorderTraversal(binaryNode.right);

		System.out.print(binaryNode.element);		
	}	
	
}
