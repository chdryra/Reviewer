package com.chdryra.android.reviewer;

import java.util.Date;

public class ReviewUser implements Review{	
	
	private RDId mID;
	private RDSubject mSubject;
	private RDRating mRating;
	
	private Author mAuthor;
	private Date mPublishDate;
	
	private RDList<RDComment> mComments;
	private RDList<RDImage> mImages;	
	private RDList<RDFact> mFacts;
	private RDList<RDUrl> mURLs;
	private RDList<RDLocation> mLocations;

	public ReviewUser(Author author, Date publishDate, String subject, float rating) {
		init(author, publishDate, subject, rating, null, null, null, null, null);
	}
	
	public ReviewUser(Author author, Date publishDate, String subject, float rating, 
			RDList<RDComment> comments, RDList<RDImage> images, RDList<RDFact> facts, RDList<RDUrl> urls, RDList<RDLocation> locations) {
		init(author, publishDate, subject, rating, comments, images, facts, urls, locations);
	}
	
	public ReviewUser(Author author, Date publishDate, ReviewEditable review) {	
		init(review.isPublished()? review.getAuthor() : author, review.isPublished()? review.getPublishDate() : publishDate, review.getSubject().get(), review.getRating().get(), 
				review.getComments(), review.getImages(), review.getFacts(), review.getURLs(), review.getLocations());
	}

	private void init(Author author, Date publishDate, String subject, float rating, 
			RDList<RDComment> comments, RDList<RDImage> images, RDList<RDFact> facts, RDList<RDUrl> urls, RDList<RDLocation> locations) {
		mID = RDId.generateID();

		mAuthor = author;
		mPublishDate = publishDate;
		
		mSubject = new RDSubject(subject, this);
		mRating = new RDRating(rating, this);
		
		mComments = comments != null? new RDList<RDComment>(comments, this) : new RDList<RDComment>();
		mImages = images != null? new RDList<RDImage>(images, this) : new RDList<RDImage>();
		mFacts = facts != null? new RDList<RDFact>(facts, this) : new RDList<RDFact>();
		mURLs = urls != null? new RDList<RDUrl>(urls, this) : new RDList<RDUrl>();
		mLocations = locations != null? new RDList<RDLocation>(locations, this) : new RDList<RDLocation>();
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
	public RDRating getRating() {
		return mRating;
	}
	
	@Override
	public ReviewTagCollection getTags() {
		return ReviewTagsManager.getTags(this);
	}
	
	@Override
	public RDList<RDImage> getImages() {
		return mImages;
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
	public boolean hasLocations() {
		return mLocations.hasData();
	}

	@Override
	public RDList<RDFact> getFacts() {
		return mFacts;
	}

	@Override
	public boolean hasFacts() {
		return mFacts.hasData();
	}
	
	@Override
	public RDList<RDComment> getComments() {
		return mComments;
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
	public boolean hasURLs() {
		return mURLs.hasData();
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

	@Override
	public Author getAuthor() {
		return mAuthor;
	}

	@Override
	public Date getPublishDate() {
		return mPublishDate;
	}

	@Override
	public boolean isPublished() {
		return mAuthor != null && mPublishDate != null;
	}
	
	@Override
	public ReviewNode publish(ReviewPublisher publisher) {
		if(!isPublished()) {
			mAuthor = publisher.getAuthor();
			mPublishDate = publisher.getPublishDate();
		}
		
		return getReviewNode();
	}

	@Override
	public ReviewNode getReviewNode() {
		return FactoryReview.createReviewNode(this);
	}
}
