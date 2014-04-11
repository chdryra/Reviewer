package com.chdryra.android.reviewer;

public interface ReviewNode extends Review {
	public Review getReview();
	
	public void setParent(Review parent);
	public void setParent(ReviewNode parentNode);
	public ReviewNode getParent();
	
	public void addChild(Review child);	
	public void addChild(ReviewNode childNode);
	
	public void addChildren(CollectionReview children);
	public void addChildren(CollectionReviewNode children);
	
	public ReviewNode getChild(RDId id);
	public CollectionReviewNode getChildren();
	public CollectionReview getChildrenReviews();
	
	public void removeChild(RDId id);
	public void removeChildren(CollectionReview children);
	public void removeChildren(CollectionReviewNode children);
	public void clearChildren();
	
	public ReviewNode getRoot();
	
	public int getDepth();
	public int getHeight();
	
	public boolean isRoot();
	public boolean isLeaf();
	public boolean isInternal();
	
	public void setRatingIsAverageOfChildren(boolean ratingIsAverage);
	public boolean isRatingIsAverageOfChildren();
	
	public void acceptVisitor(VisitorReviewNode visitorReviewNode);
}
