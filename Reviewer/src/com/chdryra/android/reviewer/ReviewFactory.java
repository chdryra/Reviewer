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
	
	private Review newSimpleReview(String title) {
		return new SimpleReview(title);	
	}
	
	private ReviewNode newReviewNode(Review review) {
		return new ReviewComponent(review);
	}
	
	public static Review createUserReview(String title) {
		return getInstance().newUserReview(title);
	}
	
	public static Review createSimpleReview(String title) {
		return getInstance().newSimpleReview(title);
	}
	
	public static ReviewNode createReviewNode(Review review) {
		return getInstance().newReviewNode(review);
	}
	
	public static ReviewNode createSimpleReviewNode(String title) {
		Review review = createSimpleReview(title);
		return createReviewNode(review);
	}
	
	public static ReviewNode createUserReviewNode(String title) {
		Review review = createUserReview(title);
		return createReviewNode(review);
	}
}
