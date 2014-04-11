package com.chdryra.android.reviewer;

public class CollectionReview extends RCollection<Review> {
	
	public CollectionReview() {
	}
	
	public void add(Review review) {
		put(review.getID(), review);
	}

	public void remove(CollectionReview reviews) {
		for(Review r : reviews)
			remove(r.getID());
	}
	
}
