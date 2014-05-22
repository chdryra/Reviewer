package com.chdryra.android.reviewer;

public class ReviewMeta implements Review {
	private RDId mID;
	private RDTitle mTitle;
	private RDRating mRating;
	
	private RCollectionReview mReviews;
	private boolean mRatingIsValid = false;
	
	private ReviewNode mNode;
	
	public ReviewMeta(String title) {
		mReviews = new RCollectionReview();
		init(title);
	}

	public ReviewMeta(String title, RCollectionReview reviews) {
		mReviews = reviews;
		init(title);
	}
	
	private void init(String title) {
		mID = RDId.generateID();
		mTitle = new RDTitle(title, this);
		mRating = new RDRating(0, this);
		mNode = FactoryReview.createReviewNode(this);
		mNode.setRatingIsAverageOfChildren(true);
	}
	
	//Review methods
	@Override
	public RDId getID() {
		return mID;
	}

	@Override
	public RDRating getRating() {
		if(!mRatingIsValid)
			mRating = getRating(new VisitorRatingAverager());
		
		return mRating;
	}

	public RDRating getRating(VisitorRatingCalculator calculator) {
		return new RDRating(calculateRating(calculator), this);
	}
	
	@Override
	public RDTitle getTitle() {
		return mTitle;
	}
	
	@Override
	public ReviewTagCollection getTags() {
		return ReviewTagsManager.getManager().getTags(mReviews);
	}
		
	@Override
	public ReviewNode getReviewNode() {
		return mNode;
	}
	
	public void addReview(Review review) {
		mReviews.add(review);
		mRatingIsValid = false;
	}
	
	public void addReviews(RCollectionReview reviews) {
		mReviews.add(reviews);
		mRatingIsValid = false;
	}
	
	public void removeReview(RDId id) {
		mReviews.remove(id);
		mRatingIsValid = false;
	}
	
	public void removeReviews(RCollectionReview reviews) {
		mReviews.remove(reviews);
		mRatingIsValid = false;
	}

	public RCollectionReview getReviews() {
		return mReviews;
	}

	@Override
	public void setRating(float rating) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setTitle(String title) {
		mTitle.set(title);
	}
	
	@Override
	public void setComments(RDCommentCollection comments) {
	}

	@Override
	public RDCommentCollection getComments() {
		TraverserReviewNode traverser = new TraverserReviewNode(getReviewNode());
		VisitorCommentCollector collector = new VisitorCommentCollector();
		traverser.setVisitor(collector);
		traverser.traverse();
		return collector.get();
	}

	@Override
	public void deleteComments() {
	}

	@Override
	public boolean hasComments() {
		RDCommentCollection comments = (RDCommentCollection)getComments();
		return comments.size() > 0;
	}

	@Override
	public RDImage getImage() {
		return null;
	}

	@Override
	public void setImage(RDImage image) {
	}

	@Override
	public void deleteImage() {
	}

	@Override
	public boolean hasImage() {
		return false;
	}

	@Override
	public RDLocation getLocation() {
		return null;
	}

	@Override
	public void setLocation(RDLocation location) {
	}

	@Override
	public void deleteLocation() {
	}

	@Override
	public boolean hasLocation() {
		return false;
	}

	@Override
	public RDFactCollection getFacts() {
		return null;
	}

	@Override
	public void setFacts(RDFactCollection data) {
	}

	@Override
	public void deleteFacts() {
	}

	@Override
	public boolean hasFacts() {
		return false;
	}

	@Override
	public RDUrl getURL() {
		return null;
	}

	@Override
	public void setURL(RDUrl url) {
	}

	@Override
	public void deleteURL() {
	}

	@Override
	public boolean hasURL() {
		return false;
	}
	

	@Override
	public RDDate getDate() {
		return null;
	}

	@Override
	public void setDate(RDDate date) {
	}

	@Override
	public void deleteDate() {
	}

	@Override
	public boolean hasDate() {
		return false;
	}
	
	@Override
	public void deleteProsCons() {
	}

	@Override
	public RDProCons getProsCons() {
		return null;
	}

	@Override
	public boolean hasProsCons() {
		return false;
	}
	
	@Override
	public void setProsCons(RDProCons prosCons) {
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null || obj.getClass() != getClass())
			return false;
		
		ReviewMeta objReview = (ReviewMeta)obj;
		if(mID.equals(objReview.mID))
			return true;
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return mID.hashCode();
	}

	private float calculateRating(VisitorRatingCalculator calculator) {
		if(calculator != null) {
			RCollectionReviewNode nodes = new RCollectionReviewNode(mReviews);
			for(ReviewNode r : nodes)
				r.acceptVisitor(calculator);
		}

		return calculator.getRating();
	}
}
