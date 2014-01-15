package com.chdryra.android.reviewer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


public class Node<T> {

	  private T mData;

	  private Node<T> mParent = null;
	  private ArrayList<Node<T>> mChildren = new ArrayList<Node<T>>();

	  public Node(Node<T> parent, T data) {
		  mParent = parent;
		  mData = data;
	  }

	  public Node<T> addChild(T childData) {
	    Node<T> child = new Node<T>(this, childData);
	    mChildren.add(child);
	    
	    return child;
	  }

	  public T getData() {
	    return mData;
	  }

	  public Node<T> getParent() {
	    return mParent;
	  }

	  public ArrayList<Node<T>> getChildren() {
		    return mChildren;
	  }
	  
	  @Override
	  public String toString() {
	    return printTree(0);
	  }

	  private static final int indent = 2;

	  private String printTree(int increment) {
	    String s = "";
	    String inc = "";
	    for (int i = 0; i < increment; ++i) {
	      inc = inc + " ";
	    }
	    s = inc + mData;
	    for (Node<T> child : mChildren) {
	      s += "\n" + child.printTree(increment + indent);
	    }
	    return s;
	  }
	}