package com.chdryra.android.reviewer;


public class ReviewUser implements ReviewEditable{	
	private RDId mID;
	private RDTitle mTitle;
	private RDRating mRating;
	
	private RDList<RDComment> mComments;
	private RDList<RDImage> mImages;	
	private RDList<RDFact> mFacts;
	private RDList<RDProCon> mProCons;
	private RDList<RDUrl> mURLs;
	private RDList<RDLocation> mLocations;

	private ReviewNode mNode;
	
	public ReviewUser(String title) {	
		mID = RDId.generateID();
		mTitle = new RDTitle(title, this);
		mRating = new RDRating(0, this);
		
		//Null option data
		mComments = new RDList<RDComment>();
		mImages = new RDList<RDImage>();
		mLocations = new RDList<RDLocation>();
		mFacts = new RDList<RDFact>();
		mProCons = new RDList<RDProCon>();
		mURLs = new RDList<RDUrl>();
		
		mNode = FactoryReview.createReviewNode(this);
	}

	@Override
	public RDId getID() {
		return mID;
	}
	
	@Override
	public RDTitle getTitle() {
		return mTitle;
	}

	@Override
	public void setTitle(String title) {
		mTitle = new RDTitle(title, this);
	}

	@Override
	public void setRating(float rating) {
		mRating = new RDRating(rating, this);
	}

	@Override
	public RDRating getRating() {
		return isRatingAverageOfCriteria()? mNode.getRating() : mRating;
	}
	
	@Override
	public ReviewTagCollection getTags() {
		return ReviewTagsManager.getTags(this);
	}

	public void setRatingAverageOfCriteria(boolean ratingIsAverage) {
		mNode.setRatingIsAverageOfChildren(ratingIsAverage);
	}
	
	public boolean isRatingAverageOfCriteria() {
		return mNode.isRatingIsAverageOfChildren();
	}
	
	public RCollectionReview getCriteria() {
		return mNode.getChildrenReviews();
	}
	
	public Review getCriterion(RDId id) {
		return mNode.getChild(id).getReview();
	}
	
	public RCollectionReviewNode getCriteriaNodes() {
		return mNode.getChildren();
	}

	@Override
	public ReviewNode getReviewNode() {
		return mNode;
	}
	
	public void setCriteria(RCollectionReview criteria) {
		mNode.clearChildren();
		mNode.addChildren(criteria);
	}

	public void addCriterion(ReviewEditable criterion) {
		mNode.addChild(criterion);
	}
	
	private <T extends RData> RDList<T> processData(RDList<T> newData, RDList<T> ifNull) {
		RDList<T> member;
		if(newData != null)
			member = newData;
		else
			member = ifNull;
		
		member.setHoldingReview(this);
		
		return member;
	}

	@Override
	public RDList<RDImage> getImages() {
		return mImages;
	}
	
	@Override
	public void setImages(RDList<RDImage> images) {
		mImages = (RDList<RDImage>)processData(images, new RDList<RDImage>());
	}
	
	@Override
	public void deleteImages() {
		setImages(null);
	}
	
	@Override
	public boolean hasImages() {
		return mImages.hasData();
	}
	
	@Override
	public RDList<RDLocation> getLocations() {
		return mLocations;
	}
	
	@Override
	public void setLocations(RDList<RDLocation> locations) {
		mLocations = (RDList<RDLocation>) processData(locations, new RDList<RDLocation>());
	}
	
	@Override
	public void deleteLocations() {
		setLocations(null);
	}
	
	@Override
	public boolean hasLocations() {
		return mLocations.hasData();
	}

	@Override
	public RDList<RDFact> getFacts() {
		return mFacts;
	}

	@Override
	public void setFacts(RDList<RDFact> facts) {
		mFacts = (RDList<RDFact>) processData(facts, new RDList<RDFact>());
	}
	
	@Override
	public void deleteFacts() {
		setFacts(null);
	}
	
	@Override
	public boolean hasFacts() {
		return mFacts.hasData();
	}
	
	@Override
	public void setComments(RDList<RDComment> comments){
		mComments = (RDList<RDComment>) processData(comments, new RDList<RDComment>());
	}

	@Override
	public RDList<RDComment> getComments() {
		return mComments;
	}

	@Override
	public void deleteComments() {
		setComments(null);
	}
	
	@Override
	public boolean hasComments() {
		return mComments.hasData();
	}

	@Override
	public RDList<RDUrl> getURLs() {
		return mURLs;
	}

	@Override
	public void setURLs(RDList<RDUrl> urls) {
		mURLs = (RDList<RDUrl>) processData(urls, new RDList<RDUrl>());
	}

	@Override
	public void deleteURLs() {
		setURLs(null);
	}

	@Override
	public boolean hasURLs() {
		return mURLs.hasData();
	}

	@Override
	public void deleteProCons() {
		setProCons(null);
	}
	
	@Override
	public RDList<RDProCon> getProCons() {
		return mProCons;
	}
	
	@Override
	public boolean hasProCons() {
		return mProCons.hasData();
	}
	
	@Override
	public void setProCons(RDList<RDProCon> proCons) {
		mProCons = (RDList<RDProCon>)processData(proCons, new RDList<RDProCon>());
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null || obj.getClass() != getClass())
			return false;
		
		ReviewUser objReview = (ReviewUser)obj;
		if(mID.equals(objReview.mID))
			return true;
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return mID.hashCode();
	}
}
