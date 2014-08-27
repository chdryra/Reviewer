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
	
	private ReviewEditable newUserReview(String title) {
		return new ReviewUser(title);	
	}
	
	private ReviewEditable newNullReview() {
		return new ReviewNull();	
	}

	private Review newMetaReview(String title) {
		return new ReviewMeta(title);
	}
	
	private ReviewNode newReviewNode(Review review) {
		return new ReviewComponent(review);
	}
	
	public static ReviewEditable createAuthoredReview(String title) {
		return getInstance().newUserReview(title);
	}
	
	public static ReviewEditable createAnonymousReview(String title) {
		return getInstance().newUserReview(title);
	}
	
	public static ReviewEditable createChildReview(String title, Review reviewEditable) {
		return getInstance().newUserReview(title);
	}
	
	public static ReviewEditable createNullReview() {
		return getInstance().newNullReview();
	}
	
	public static Review createMetaReview(String title) {
		return getInstance().newMetaReview(title);
	}
	
	public static ReviewNode createReviewNode(Review review) {
		return getInstance().newReviewNode(review);
	}
	
	public static ReviewNode createAuthoredUserReviewNode(String title) {
		ReviewEditable reviewEditable = createAuthoredReview(title);
		return createReviewNode(reviewEditable);
	}
	
	public static ReviewNode createAnonymousUserReviewNode(String title) {
		ReviewEditable reviewEditable = createAnonymousReview(title);
		return createReviewNode(reviewEditable);
	}
	
	public static ReviewNode createMetaReviewNode(String title) {
		Review review = createMetaReview(title);
		return createReviewNode(review);
	}
	
	public static ReviewNode createNullReviewNode() {
		ReviewEditable reviewEditable = createNullReview();
		return createReviewNode(reviewEditable);
	}
	
	public static ReviewEditable createEditableReview() {
		return createAnonymousReview("");
	}
}
