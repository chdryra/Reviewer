package com.chdryra.android.reviewer;

public interface ReviewNode extends Review {
	public Review getReview();
	
	public void setParent(Review parent);
	public void setParent(ReviewNode parentNode);
	public ReviewNode getParent();
	
	public void addChild(Review child);	
	public void addChild(ReviewNode childNode);
	public void removeChild(Review child);
	
	public void addChildren(ReviewNodeCollection children);
	public ReviewNodeCollection getChildren();
	
	public void acceptVisitor(VisitorReviewNode visitorReviewNode);
}
