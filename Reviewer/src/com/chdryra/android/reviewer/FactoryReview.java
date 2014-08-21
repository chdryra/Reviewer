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
	
	private Review newUserReview(String title, Author author) {
		return new ReviewUser(title, author);	
	}
	
	private Review newNullReview() {
		return new ReviewNull();	
	}

	private Review newMetaReview(String title, Author author) {
		return new ReviewMeta(title, author);
	}
	
	private ReviewNode newReviewNode(Review review) {
		return new ReviewComponent(review);
	}
	
	public static Review createAuthoredReview(String title) {
		return getInstance().newUserReview(title, Administrator.getCurrentAuthor());
	}
	
	public static Review createAnonymousReview(String title) {
		return getInstance().newUserReview(title, Administrator.getAnonymousAuthor());
	}
	
	public static Review createChildReview(String title, Review review) {
		return getInstance().newUserReview(title, review.getAuthor());
	}
	
	public static Review createNullReview() {
		return getInstance().newNullReview();
	}
	
	public static Review createAuthoredMetaReview(String title) {
		return getInstance().newMetaReview(title, Administrator.getCurrentAuthor());
	}
	
	public static Review createAnonymousMetaReview(String title) {
		return getInstance().newMetaReview(title, Administrator.getAnonymousAuthor());
	}
	
	public static ReviewNode createReviewNode(Review review) {
		return getInstance().newReviewNode(review);
	}
	
	public static ReviewNode createAuthoredUserReviewNode(String title) {
		Review review = createAuthoredReview(title);
		return createReviewNode(review);
	}
	
	public static ReviewNode createAnonymousUserReviewNode(String title) {
		Review review = createAnonymousReview(title);
		return createReviewNode(review);
	}
	
	public static ReviewNode createAuthoredMetaReviewNode(String title) {
		Review review = createAuthoredMetaReview(title);
		return createReviewNode(review);
	}
	
	public static ReviewNode createAnonymousMetaReviewNode(String title) {
		Review review = createAnonymousMetaReview(title);
		return createReviewNode(review);
	}
	
	public static ReviewNode createNullReviewNode() {
		Review review = createNullReview();
		return createReviewNode(review);
	}
	
	public static Review createUserReviewInProgress() {
		return createAnonymousReview("");
	}
}
