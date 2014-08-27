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
	
	//ReviewEditable methods
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
	
	public RDList<RDRating> getRatings() {
		RDList<RDRating> ratings = new RDList<RDRating>();
		for(Review r : mReviews)
			ratings.add(r.getRating());
		
		return ratings;
	}
	
	@Override
	public RDTitle getTitle() {
		return mTitle;
	}
	
	public RDList<RDTitle> getTitles() {
		RDList<RDTitle> titles = new RDList<RDTitle>();
		for(Review r : mReviews)
			titles.add(r.getTitle());
		
		return titles;
	}

	@Override
	public ReviewTagCollection getTags() {
		return ReviewTagsManager.getManager().getTags(mReviews);
	}
		
	@Override
	public ReviewNode getReviewNode() {
		return mNode;
	}
	
	public void addReview(ReviewEditable reviewEditable) {
		mReviews.add(reviewEditable);
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
	public RDList<RDComment> getComments() {
		RDList<RDComment> comments = new RDList<RDComment>();
		for(Review r : mReviews)
			comments.add(r.getComments());
		
		return comments;
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
	public boolean hasImages() {
		return getImages().hasData();
	}

	@Override
	public RDList<RDLocation> getLocations() {
		return null;
	}

	@Override
	public boolean hasLocations() {
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
	public boolean hasFacts() {
		return getFacts().hasData();
	}

	@Override
	public RDList<RDUrl> getURLs() {
		RDList<RDUrl> urls = new RDList<RDUrl>();
		for(Review r : mReviews)
			urls.add(r.getURLs());
		
		return urls;
	}

	@Override
	public boolean hasURLs() {
		return getURLs().hasData();
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
