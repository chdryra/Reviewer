package com.chdryra.android.reviewer;

/*
 * Copyright 2007 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
  * @author ycoppel@google.com (Yohann Coppel)
  * 
  * @param <T>
  *          Object's type in the tree.
*/
public class TreeNode<T extends Parcelable> implements Parcelable{
	private static final String CHILDREN = "com.chdryra.android.reviewer.TreeNode_children";
	
	private T mHead;
	  private TreeNode<T> mParent = null;
	  private LinkedHashMap<T, TreeNode<T>> mChildren = new LinkedHashMap<T, TreeNode<T>>();
	
	  public TreeNode(T head) {
	    mHead = head;
	  }
	
	  public void addChild(T child) {
	    TreeNode<T> childNode = new TreeNode<T>(child);
	    mChildren.put(child, childNode);
	    childNode.mParent = this;
	  }
	
	  public void addChild(TreeNode<T> childNode) {
		    mChildren.put(childNode.getHead(), childNode);
		    childNode.mParent = this;
	  }
	  
	  public void removeChild(T child) {
		mChildren.remove(child);
	  }
	  
	  public void setParent(T parent) {
	    TreeNode<T> parentNode = new TreeNode<T>(parent);
	    parentNode.mChildren.put(mHead, this);
	    this.mParent = parentNode;
	  }
	
	  public void setParent(TreeNode<T> parentNode) {
	    parentNode.mChildren.put(mHead, this);
	    this.mParent = parentNode;
	  }
	
	  public T getHead() {
	    return mHead;
	  }
	
	  public TreeNode<T> getParent() {
	    return mParent;
	  }
	
	  public Collection<TreeNode<T>> getChildren() {
	    return mChildren.values();
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
	    s = inc + mHead;
	    for (TreeNode<T> child : mChildren.values()) {
	      s += "\n" + child.printTree(increment + indent);
	    }
	    return s;
	  }

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		mHead.writeToParcel(dest, flags);
		mParent.writeToParcel(dest, flags);
		Bundle args = new Bundle();
		args.putSerializable(CHILDREN, mChildren);
		dest.writeBundle(args);
	}
	
	 public static final Parcelable.Creator<TreeNode<T>> CREATOR
     	= new Parcelable.Creator<TreeNode<T>>() {
 public MyParcelable createFromParcel(Parcel in) {
     return new MyParcelable(in);
 }

 public MyParcelable[] newArray(int size) {
     return new MyParcelable[size];
 }
};

private MyParcelable(Parcel in) {
 mData = in.readInt();
}
}