package com.chdryra.android.reviewer;

public interface ReviewNode extends Review {
	public Review getReview();
	
	public void setParent(Review parent);
	public void setParent(ReviewNode parentNode);
	public ReviewNode getParent();
	
	public void addChild(Review child);	
	public void addChild(ReviewNode childNode);
	
	public void addChildren(RCollectionReview children);
	public void addChildren(RCollectionReviewNode children);
	
	public ReviewNode getChild(RDId id);
	public RCollectionReviewNode getChildren();
	public RCollectionReview getChildrenReviews();
	
	public RCollectionReviewNode getDescendents();
	
	public void removeChild(RDId id);
	public void removeChildren(RCollectionReview children);
	public void removeChildren(RCollectionReviewNode children);
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
