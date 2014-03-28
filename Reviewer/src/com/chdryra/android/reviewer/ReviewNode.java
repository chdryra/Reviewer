package com.chdryra.android.reviewer;

public interface ReviewNode extends Review {
	
	public void setParent(Review parent);
	public ReviewNode getParent();
	
	public void addChild(Review child);	
	public void addChildren(ReviewCollection children);
	public ReviewCollection getChildren();
}
