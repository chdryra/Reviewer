/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

class FactoryReview {

	private static FactoryReview sFactory = null;
	
	private FactoryReview() {
	}
	
	private static FactoryReview getInstance() {
		if(sFactory == null)
			sFactory = new FactoryReview();
		
		return sFactory;
	}
	
	private ReviewEditable newReviewTreeEditable(String subject) {
		return new ReviewTreeEditable(subject);	
	}
	
	private ReviewEditable newReviewUserEditable(String subject) {
		return new ReviewUserEditable(subject);	
	}
	
	private ReviewEditable newNullReview() {
		return new ReviewNull();	
	}

	private ReviewNode newReviewNode(Review review) {
		return new ReviewComponent(review);
	}
	
	public static ReviewEditable createReviewInProgress() {
		return createReviewTreeEditable("");
	}
	
	public static ReviewEditable createReviewTreeEditable(String subject) {
		return getInstance().newReviewTreeEditable(subject);
	}
	
	public static ReviewEditable createReviewUserEditable(String subject) {
		return getInstance().newReviewUserEditable(subject);
	}
	
	public static ReviewNode createReviewNode(Review review) {
		return getInstance().newReviewNode(review);
	}
	
	public static ReviewEditable createNullReview() {
		return getInstance().newNullReview();
	}
	
	public static ReviewNode createNullReviewNode() {
		ReviewEditable reviewEditable = createNullReview();
		return createReviewNode(reviewEditable);
	}
}
