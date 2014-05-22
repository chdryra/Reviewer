package com.chdryra.android.reviewer;

public class ReviewBasic implements Review {
	
	private RDId mID;
	private RDTitle mTitle;
	private RDRating mRating;
	
	private ReviewNode mNode;
	
	public ReviewBasic(String title) {
		mID = RDId.generateID();
		mTitle = new RDTitle(title, this);
		mRating = new RDRating(0, this);
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
	public RDRating getRating() {
		return mRating;
	}

	@Override
	public void setRating(float rating) {
		mRating.set(rating);
	}

	@Override
	public ReviewNode getReviewNode() {
		return mNode;
	}
	
	@Override
	public void setComments(RDComments comments) {
	}

	@Override
	public RDComments getComments() {
		return null;
	}

	@Override
	public void deleteComments() {
	}

	@Override
	public boolean hasComments() {
		return false;
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
	public RDFacts getFacts() {
		return null;
	}

	@Override
	public void setFacts(RDFacts facts) {
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
	public RDProsCons getProsCons() {
		return null;
	}

	@Override
	public boolean hasProsCons() {
		return false;
	}
	
	@Override
	public void setProsCons(RDProsCons prosCons) {
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null || obj.getClass() != getClass())
			return false;
		
		ReviewBasic objReview = (ReviewBasic)obj;
		if(mID.equals(objReview.mID))
			return true;
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return mID.hashCode();
	}

	@Override
	public ReviewTagCollection getTags() {
		return ReviewTagsManager.getTags(this);
	}
}
