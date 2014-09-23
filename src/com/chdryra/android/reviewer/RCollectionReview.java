/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

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
