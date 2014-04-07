package com.chdryra.android.reviewer;

public interface ReviewNode extends Review {
	public Review getReview();
	
	public void setParent(Review parent);
	public void setParent(ReviewNode parentNode);
	public ReviewNode getParent();
	
	public void addChild(Review child);	
	public void addChild(ReviewNode childNode);
	
	public void addChildren(ReviewCollection children);
	public void addChildren(ReviewNodeCollection children);
	
	public ReviewNode getChild(ReviewID id);
	public ReviewNodeCollection getChildren();
	public ReviewCollection getChildrenReviews();
	
	public void removeChild(ReviewID id);
	public void removeChildren(ReviewCollection children);
	public void removeChildren(ReviewNodeCollection children);
	public void clearChildren();
	
	public void acceptVisitor(VisitorReviewNode visitorReviewNode);
}
