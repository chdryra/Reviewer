package com.chdryra.android.reviewer;

public class RCollectionReviewNode extends RCollection<ReviewNode> {
	public RCollectionReviewNode() {
	}

	public RCollectionReviewNode(RCollectionReview reviews) {
		for(Review r : reviews)
			add(FactoryReview.createReviewNode(r));
	}
	
	public void add(ReviewNode reviewNode) {
		put(reviewNode.getID(), reviewNode);
	}
	
	public RCollectionReview getReviews() {
		RCollectionReview reviews = new RCollectionReview();
		for(ReviewNode node : this)
			reviews.add(node.getReview());
		
		return reviews;
	}
}	