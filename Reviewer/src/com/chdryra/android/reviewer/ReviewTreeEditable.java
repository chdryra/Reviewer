package com.chdryra.android.reviewer;

public class ReviewTreeEditable extends ReviewEditable {	

	private ReviewNode mNode;
	
	private VisitorRatingCalculator mRatingCalculator = new VisitorRatingGetter();
	private RDRating mRatingCache;
	private boolean mRatingIsValid = false;
	
	public ReviewTreeEditable(String subject) {	
		init(subject);
	}
	
	public ReviewTreeEditable(String subject, VisitorRatingCalculator ratingCalculator) {
		mRatingCalculator = ratingCalculator;
		init(subject);
	}
	
	private void init(String subject) {
		mNode = FactoryReview.createReviewNode(FactoryReview.createReviewUserEditable(subject));
		mNode.setRatingIsAverageOfChildren(false);
		mRatingCache = new RDRating(0, this);
		calculateRating();	
	}
	
	private ReviewEditable getReviewEditable() {
		return (ReviewEditable)mNode.getReview();
	}
	
	@Override
	public RDId getID() {
		return mNode.getID();
	}
	
	@Override
	public RDSubject getSubject() {
		return mNode.getSubject();
	}

	@Override
	public void setSubject(String subject) {
		getReviewEditable().setSubject(subject);
	}

	@Override
	public void setRating(float rating) {
		getReviewEditable().setRating(rating);
	}

	@Override
	public RDRating getRating() {
		if(!mRatingIsValid)
			calculateRating();
			
		return mRatingCache;
	}
	
	private void calculateRating() {
		mRatingCalculator.clear();
		mNode.acceptVisitor(mRatingCalculator);
		mRatingCache.set(mRatingCalculator.getRating());
	}
	
	@Override
	public ReviewTagCollection getTags() {
		return ReviewTagsManager.getTags(this);
	}
	
	@Override
	public ReviewNode getReviewNode() {
		return mNode;
	}
	
	@Override
	public RDList<RDImage> getImages() {
		return mNode.getImages();
	}
	
	@Override
	public void setImages(RDList<RDImage> images) {
		getReviewEditable().setImages(images);
	}
	
	@Override
	public void deleteImages() {
		getReviewEditable().deleteImages();
	}
	
	@Override
	public boolean hasImages() {
		return mNode.hasImages();
	}
	
	@Override
	public RDList<RDLocation> getLocations() {
		return mNode.getLocations();
	}
	
	@Override
	public void setLocations(RDList<RDLocation> locations) {
		getReviewEditable().setLocations(locations);
	}
	
	@Override
	public void deleteLocations() {
		getReviewEditable().deleteLocations();
	}
	
	@Override
	public boolean hasLocations() {
		return mNode.hasLocations();
	}

	@Override
	public RDList<RDFact> getFacts() {
		return mNode.getFacts();
	}

	@Override
	public void setFacts(RDList<RDFact> facts) {
		getReviewEditable().setFacts(facts);
	}
	
	@Override
	public void deleteFacts() {
		getReviewEditable().deleteFacts();
	}
	
	@Override
	public boolean hasFacts() {
		return mNode.hasFacts();
	}
	
	@Override
	public void setComments(RDList<RDComment> comments){
		getReviewEditable().setComments(comments);
	}

	@Override
	public RDList<RDComment> getComments() {
		return mNode.getComments();
	}

	@Override
	public void deleteComments() {
		getReviewEditable().deleteComments();
	}
	
	@Override
	public boolean hasComments() {
		return mNode.hasComments();
	}

	@Override
	public RDList<RDUrl> getURLs() {
		return mNode.getURLs();
	}

	@Override
	public void setURLs(RDList<RDUrl> urls) {
		getReviewEditable().setURLs(urls);
	}

	@Override
	public void deleteURLs() {
		getReviewEditable().deleteURLs();
	}

	@Override
	public boolean hasURLs() {
		return mNode.hasURLs();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null || obj.getClass() != getClass())
			return false;
		
		ReviewTreeEditable objReview = (ReviewTreeEditable)obj;
		if(mNode.equals(objReview.mNode))
			return true;
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return mNode.hashCode();
	}

	@Override
	public ReviewNode publish(ReviewPublisher publisher) {
		return publisher.publish(this);
	}
}
