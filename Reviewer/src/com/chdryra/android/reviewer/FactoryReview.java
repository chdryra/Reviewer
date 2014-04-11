package com.chdryra.android.reviewer;

public class FactoryReview {

	private static FactoryReview sFactory = null;
	
	private FactoryReview() {
		
	}
	
	public static FactoryReview getInstance() {
		if(sFactory == null)
			sFactory = new FactoryReview();
		
		return sFactory;
	}
	
	private Review newUserReview(String title) {
		return new ReviewUser(title);	
	}
	
	private Review newNullReview() {
		return new ReviewNull();	
	}
	
	private Review newBasicReview(String title) {
		return new ReviewBasic(title);	
	}
	
	private Review newMetaReview(String title) {
		return new ReviewMeta(title);
	}
	
	private ReviewNode newReviewNode(Review review) {
		return new ReviewComponent(review);
	}
	
	public static Review createUserReview(String title) {
		return getInstance().newUserReview(title);
	}
	
	public static Review createNullReview() {
		return getInstance().newNullReview();
	}
	
	public static Review createBasicReview(String title) {
		return getInstance().newBasicReview(title);
	}
	
	public static Review createMetaReview(String title) {
		return getInstance().newMetaReview(title);
	}
	
	public static ReviewNode createReviewNode(Review review) {
		return getInstance().newReviewNode(review);
	}
	
	public static ReviewNode createBasicReviewNode(String title) {
		Review review = createBasicReview(title);
		return createReviewNode(review);
	}
	
	public static ReviewNode createUserReviewNode(String title) {
		Review review = createUserReview(title);
		return createReviewNode(review);
	}
	
	public static ReviewNode createMetaReviewNode(String title) {
		Review review = createMetaReview(title);
		return createReviewNode(review);
	}
	
	public static ReviewNode createNullReviewNode() {
		Review review = createNullReview();
		return createReviewNode(review);
	}
}
