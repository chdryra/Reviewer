package com.chdryra.android.reviewer;

public class ReviewUser implements Review{	
	private RDId mID;
	private RDTitle mTitle;
	private RDRating mRating;
	
	private RDList<RDComment> mComments;
	private RDList<RDImage> mImages;
	private RDLocation mLocation;	
	private RDList<RDFact> mFacts;
	private RDUrl mURL;
	private RDDate mDate;
	private RDList<RDProCon> mProCons;

	private ReviewNode mNode;
	
	public ReviewUser(String title) {	
		mID = RDId.generateID();
		mTitle = new RDTitle(title, this);
		mRating = new RDRating(0, this);
		
		//Null option data
		mComments = new RDList<RDComment>();
		mImages = new RDList<RDImage>();
		mLocation = new RDLocation();
		mFacts = new RDList<RDFact>();
		mProCons = new RDList<RDProCon>();
		mURL = new RDUrl();
		mDate = new RDDate();
		
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
		mTitle.set(title);
	}

	@Override
	public void setRating(float rating) {
		mRating.set(rating);
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

	public void addCriterion(Review criterion) {
		mNode.addChild(criterion);
	}
	
	private RData processData(RData newData, RData ifNull) {
		RData member;
		if(newData != null)
			member = newData;
		else
			member = ifNull;
		
		member.setHoldingReview(this);
		
		return member;
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
	
	public void deleteImages() {
		setImages(null);
	}
	
	public boolean hasImages() {
		return mImages.hasData();
	}
	
	public RDLocation getLocation() {
		return mLocation;
	}
	
	public void setLocation(RDLocation location) {
		mLocation = (RDLocation) processData(location, new RDLocation());
	}
	
	public void deleteLocation() {
		setLocation(null);
	}
	
	public boolean hasLocation() {
		return mLocation.hasData();
	}

	public RDList<RDFact> getFacts() {
		return mFacts;
	}

	public void setFacts(RDList<RDFact> facts) {
		mFacts = (RDList<RDFact>) processData(facts, new RDList<RDFact>());
	}
	
	public void deleteFacts() {
		setFacts(null);
	}
	
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
	public RDUrl getURL() {
		return mURL;
	}

	@Override
	public void setURL(RDUrl url) {
		mURL = (RDUrl) processData(url, new RDUrl());
	}

	@Override
	public void deleteURL() {
		setURL(null);
	}

	@Override
	public boolean hasURL() {
		return mURL.hasData();
	}

	@Override
	public RDDate getDate() {
		return mDate;
	}
	
	@Override
	public void setDate(RDDate date) {
		mDate = (RDDate) processData(date, new RDDate());
	}
	
	@Override
	public void deleteDate() {
		setDate(null);
	}
	
	@Override
	public boolean hasDate() {
		return mDate.hasData();
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
