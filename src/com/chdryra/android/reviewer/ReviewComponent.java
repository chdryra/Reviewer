/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import java.util.Date;

public class ReviewComponent implements ReviewNode {
	private final RDId mID;
	
	private final Review mReview;
	private ReviewNode mParent;
	private final RCollectionReviewNode mChildren;

	private boolean mRatingIsAverage = false;
	
	public ReviewComponent(Review review) {
		mID = RDId.generateID();
		mReview = review;
		mChildren = new RCollectionReviewNode();
	}
	
	//ReviewNode methods
	@Override
	public Review getReview() {
		return mReview;
	}
	
	@Override
	public void setParent(Review parent) {
	    setParent(new ReviewComponent(parent));
	}
	
	@Override
	public void setParent(ReviewNode parentNode) {
		if(mParent != null && parentNode != null && mParent.getID().equals(parentNode.getID()))
			return;
		
		mParent = parentNode;
		if(mParent != null)
			mParent.addChild(this);
	}
	
	@Override
	public ReviewNode getParent() {
		return mParent;
	}
	
	@Override
	public void addChild(Review child) {
		addChild(new ReviewComponent(child));
	}

	@Override
	public void addChild(ReviewNode childNode) {
	    if(mChildren.containsID(childNode.getID()))
	    	return;
		mChildren.put(childNode.getID(), childNode);
	    childNode.setParent(this);
	}
	
	@Override
	public ReviewNode getChild(RDId id) {
		return mChildren.get(id);
	}
	
	@Override
	public void removeChild(RDId id) {
		ReviewNode child = getChild(id);
		child.setParent(null);
		mChildren.remove(child.getID());
	}
	
	@Override
	public void addChildren(RCollectionReview children) {
		for(Review child: children)
			addChild(child);
	}
	
	@Override
	public void addChildren(RCollectionReviewNode children) {
		for(ReviewNode childNode: children)
			addChild(childNode);
	}

	@Override
	public void removeChildren(RCollectionReviewNode children) {
		for(Review child: children)
			removeChild(child.getID());
	}
	
	@Override
	public void removeChildren(RCollectionReview children) {
		for(Review child: children)
			removeChild(child.getID());
	}
	
	@Override
	public void clearChildren() {
		RCollectionReviewNode children = new RCollectionReviewNode();
		children.add(getChildren());
		for(ReviewNode child : children)
			removeChild(child.getID());
	}
	
	@Override
	public RCollectionReviewNode getChildren() {
		return mChildren;
	}
	
	@Override
	public RCollectionReviewNode getDescendents() {
		TraverserReviewNode traverser = new TraverserReviewNode(this);
		VisitorNodeCollector collector = new VisitorNodeCollector();
		traverser.setVisitor(collector);
		traverser.traverse();
		
		return collector.get();
	}
	
	@Override
	public RCollectionReview getChildrenReviews() {
		RCollectionReviewNode childNodes = getChildren();
		RCollectionReview childReviews = new RCollectionReview();
		for(ReviewNode child : childNodes)
			childReviews.add(child.getReview());
		
		return childReviews;
	}
	
	@Override
	public boolean isRatingIsAverageOfChildren() {
		return mRatingIsAverage;
	}
	
	@Override
	public void setRatingIsAverageOfChildren(boolean ratingIsAverage) {
		mRatingIsAverage = ratingIsAverage;
	}
	
	@Override
	public ReviewNode getRoot() {
		ReviewNode root = this;
		while(root.getParent() != null)
			root = root.getParent();
		
		return root;
	}

	@Override
	public int getDepth() {
		int depth = 0;
		if(mParent != null)
			depth = 1 + mParent.getDepth();
		
		return depth;
	}

	@Override
	public int getHeight() {
		int height = 0;
		for(ReviewNode child : getChildren())
			height = Math.max(height, child.getHeight());
		
		return height;
	}

	@Override
	public boolean isRoot() {
		return mParent == null;
	}

	@Override
	public boolean isLeaf() {
		return getChildren().size() == 0;
	}

	@Override
	public boolean isInternal() {
		return !(isRoot() || isLeaf());
	}

	@Override
	public void acceptVisitor(VisitorReviewNode visitorReviewNode) {
		visitorReviewNode.visit(this);
	}
	
	//ReviewEditable methods
	@Override
	public ReviewNode getReviewNode() {
		return this;
	}

	@Override
	public RDId getID() {
		//return mID;
		return mReview.getID();
	}
	
	@Override
	public RDSubject getSubject() {
		return mReview.getSubject();
	}

	@Override
	public RDRating getRating() {
		return isRatingIsAverageOfChildren()? getAverageRatingOfChildren() : mReview.getRating();
	}

	private RDRating getAverageRatingOfChildren() {
		VisitorRatingCalculator visitor = new VisitorRatingAverageOfChildren();
		acceptVisitor(visitor);
		return new RDRating(visitor.getRating(), this);
	}

	@Override
	public RDList<RDComment> getComments() {
		return mReview.getComments();
	}

	@Override
	public boolean hasComments() {
		return mReview.hasComments();
	}

	@Override
	public RDList<RDImage> getImages() {
		return mReview.getImages();
	}

	@Override
	public boolean hasImages() {
		return mReview.hasImages();
	}

	@Override
	public RDList<RDLocation> getLocations() {
		return mReview.getLocations();
	}

	@Override
	public boolean hasLocations() {
		return mReview.hasLocations();
	}

	@Override
	public RDList<RDFact> getFacts() {
		return mReview.getFacts();
	}

	@Override
	public boolean hasFacts() {
		return mReview.hasFacts();
	}

	@Override
	public RDList<RDUrl> getURLs() {
		return mReview.getURLs();
	}

	@Override
	public boolean hasURLs() {
		return mReview.hasURLs();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null || obj.getClass() != getClass())
			return false;
		
		ReviewComponent objNode = (ReviewComponent)obj;
		return mID.equals(objNode.mID);
	}
	
	@Override
	public int hashCode() {
		return mID.hashCode();
	}

	@Override
	public ReviewTagCollection getTags() {
		return mReview.getTags();
	}
	
	@Override
	public Author getAuthor() {
		return mReview.getAuthor();
	}
	
	@Override
	public Date getPublishDate() {
		return mReview.getPublishDate();
	}
	
	@Override
	public boolean isPublished() {
		return mReview.isPublished();
	}

	@Override
	public ReviewNode publish(ReviewTreePublisher publisher) {
		return mReview.publish(publisher);
	}
}
