package com.chdryra.android.reviewer;

public class ReviewUser implements Review{	
	private RDId mID;
	private RDTitle mTitle;
	private RDRating mRating;
	
	private RDComments mComments;
	private RDImage mImage;
	private RDLocation mLocation;	
	private RDFacts mFacts;
	private RDUrl mURL;
	private RDDate mDate;
	private RDProsCons mProsCons;

	private ReviewNode mNode;
	
	public ReviewUser(String title) {	
		mID = RDId.generateID();
		mTitle = new RDTitle(title, this);
		mRating = new RDRating(0, this);
		
		//Null option data
		mComments = new RDComments();
		mImage = new RDImage();
		mLocation = new RDLocation();
		mFacts = new RDFacts();
		mProsCons = new RDProsCons();
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
	
	public RDImage getImage() {
		return mImage;
	}
	
	public void setImage(RDImage image) {
		mImage = (RDImage)processData(image, new RDImage());
	}
	
	public void deleteImage() {
		setImage(null);
	}
	
	public boolean hasImage() {
		return mImage.hasData();
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

	public RDFacts getFacts() {
		return mFacts;
	}

	public void setFacts(RDFacts facts) {
		mFacts = (RDFacts) processData(facts, new RDFacts());
	}
	
	public void deleteFacts() {
		setFacts(null);
	}
	
	public boolean hasFacts() {
		return mFacts.hasData();
	}
	
	@Override
	public void setComments(RDComments comments){
		mComments = (RDComments) processData(comments, new RDComments());
	}

	@Override
	public RDComments getComments() {
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
	public void deleteProsCons() {
		setProsCons(null);
	}
	
	@Override
	public RDProsCons getProsCons() {
		return mProsCons;
	}
	
	@Override
	public boolean hasProsCons() {
		return mProsCons.hasData();
	}
	
	@Override
	public void setProsCons(RDProsCons prosCons) {
		mProsCons = (RDProsCons)processData(prosCons, new RDProsCons());
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
