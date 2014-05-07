package com.chdryra.android.reviewer;

public class RCollectionReview extends RCollection<Review> {
	
	public RCollectionReview() {
	}
	
	public void add(Review review) {
		put(review.getID(), review);
	}

	public void remove(RCollectionReview reviews) {
		for(Review r : reviews)
			remove(r.getID());
	}
	
}
