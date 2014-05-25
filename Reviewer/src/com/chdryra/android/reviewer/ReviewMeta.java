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
	public void setComments(RDList<RDComment> comments) {
		throw new UnsupportedOperationException();
	}

	@Override
	public RDList<RDComment> getComments() {
		RDList<RDComment> comments = new RDList<RDComment>();
		for(Review r : mReviews)
			comments.add(r.getComments());
		
		return comments;
	}

	@Override
	public void deleteComments() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasComments() {
		return getComments().hasData();
	}

	@Override
	public RDList<RDImage> getImages() {
		RDList<RDImage> images = new RDList<RDImage>();
		for(Review r : mReviews)
			images.add(r.getImages());
		
		return images;
	}

	@Override
	public void setImages(RDList<RDImage> image) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteImages() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasImages() {
		return getImages().hasData();
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
	public RDList<RDFact> getFacts() {
		RDList<RDFact> facts = new RDList<RDFact>();
		for(Review r : mReviews)
			facts.add(r.getFacts());
		
		return facts;
	}

	@Override
	public void setFacts(RDList<RDFact> data) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteFacts() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasFacts() {
		return getFacts().hasData();
	}

	@Override
	public RDUrl getURL() {
		return null;
	}

	@Override
	public void setURL(RDUrl url) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteURL() {
		throw new UnsupportedOperationException();
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
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteDate() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasDate() {
		return false;
	}
	
	@Override
	public void deleteProCons() {
		throw new UnsupportedOperationException();
	}

	@Override
	public RDList<RDProCon> getProCons() {
		RDList<RDProCon> proCons = new RDList<RDProCon>();
		for(Review r : mReviews)
			proCons.add(r.getProCons());
		
		return proCons;
	}

	@Override
	public boolean hasProCons() {
		return getProCons().hasData();
	}
	
	@Override
	public void setProCons(RDList<RDProCon> proCons) {
		throw new UnsupportedOperationException();
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
