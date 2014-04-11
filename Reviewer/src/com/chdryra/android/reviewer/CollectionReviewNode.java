package com.chdryra.android.reviewer;

public class CollectionReviewNode extends RCollection<ReviewNode> {
	public CollectionReviewNode() {
	}

	public CollectionReviewNode(CollectionReview reviews) {
		for(Review r : reviews)
			add(FactoryReview.createReviewNode(r));
	}
	
	public void add(ReviewNode reviewNode) {
		put(reviewNode.getID(), reviewNode);
	}
}	