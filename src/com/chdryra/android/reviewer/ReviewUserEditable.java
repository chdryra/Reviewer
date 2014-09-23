/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

public class ReviewUserEditable extends ReviewEditable {	
	private RDId mID;
	private RDSubject mSubject;
	private RDRating mRating;
	
	private RDList<RDComment> mComments;
	private RDList<RDImage> mImages;	
	private RDList<RDFact> mFacts;
	private RDList<RDUrl> mURLs;
	private RDList<RDLocation> mLocations;

	public ReviewUserEditable(String subject) {	
		init(subject);
	}

	private void init(String subject) {
		mID = RDId.generateID();
		mSubject = new RDSubject(subject, this);
		mRating = new RDRating(0, this);
		
		//Null option data
		mComments = new RDList<RDComment>();
		mImages = new RDList<RDImage>();
		mLocations = new RDList<RDLocation>();
		mFacts = new RDList<RDFact>();
		mURLs = new RDList<RDUrl>();		
	}
	
	@Override
	public RDId getID() {
		return mID;
	}
	
	@Override
	public RDSubject getSubject() {
		return mSubject;
	}

	@Override
	public void setSubject(String subject) {
		mSubject = new RDSubject(subject, this);
	}

	@Override
	public void setRating(float rating) {
		mRating = new RDRating(rating, this);
	}

	@Override
	public RDRating getRating() {
		return mRating;
	}
	
	@Override
	public ReviewTagCollection getTags() {
		return ReviewTagsManager.getTags(this);
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
		mImages = processData(images, new RDList<RDImage>());
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
		mLocations = processData(locations, new RDList<RDLocation>());
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
		mFacts = processData(facts, new RDList<RDFact>());
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
		mComments = processData(comments, new RDList<RDComment>());
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
		mURLs = processData(urls, new RDList<RDUrl>());
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
	public boolean equals(Object obj) {
		if(obj == null || obj.getClass() != getClass())
			return false;
		
		ReviewUserEditable objReview = (ReviewUserEditable)obj;
		return mID.equals(objReview.mID)? true : false;
	}
	
	@Override
	public int hashCode() {
		return mID.hashCode();
	}

	@Override
	public ReviewNode publish(ReviewTreePublisher publisher) {
		Review review = new ReviewUser(publisher.getAuthor(), publisher.getPublishDate(), this); 
		
		return publisher.publish(review);
	}

	@Override
	public ReviewNode getReviewNode() {
		return FactoryReview.createReviewNode(this);
	}
}
