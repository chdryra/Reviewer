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
	
	private Review newUserReview(String title) {
		return new UserReview(title);	
	}
	
	private ReviewNode newReviewNode(Review review) {
		return new ReviewComponent(review);
	}
	
	public static Review createUserReview(String title) {
		return getInstance().newUserReview(title);
	}
	
	public static ReviewNode createReviewNode(Review review) {
		return getInstance().newReviewNode(review);
	}
	
	public static ReviewNode createReviewNode(String title) {
		Review review = getInstance().newUserReview(title);
		return getInstance().newReviewNode(review);
	}
}
