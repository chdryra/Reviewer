package com.chdryra.android.reviewer;

public class ReviewFactory {

	private static ReviewFactory sFactory = null;
	
	private ReviewFactory() {
		
	}
	
	public static ReviewFactory getInstance() {
		if(sFactory == null)
			sFactory = new ReviewFactory();
		
		return sFactory;
	}
	
	public Review createSimpleReview(String title) {
		return new SimpleReview(title);
	}
	
	public Review createMainReview(String title) {
		return new MainReview(title);
	}
	
	public Review createReviewNode(Review review) {
		return new ReviewComponent(review);
	}
}
