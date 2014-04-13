package com.chdryra.android.reviewer;

import android.os.Parcel;
import android.os.Parcelable;

public class ReviewMeta implements Review {
	private RDId mID;
	private RDTitle mTitle;
	private RDRating mRating;
	
	private CollectionReview mReviews;
	private boolean mRatingIsValid = false;
	
	private ReviewNode mNode;
	
	public ReviewMeta(String title) {
		mReviews = new CollectionReview();
		init(title);
	}

	public ReviewMeta(String title, CollectionReview reviews) {
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
	public ReviewNode getReviewNode() {
		return mNode;
	}
	
	public void addReview(Review review) {
		mReviews.add(review);
		mRatingIsValid = false;
	}
	
	public void addReviews(CollectionReview reviews) {
		mReviews.add(reviews);
		mRatingIsValid = false;
	}
	
	public void removeReview(RDId id) {
		mReviews.remove(id);
		mRatingIsValid = false;
	}
	
	public void removeReviews(CollectionReview reviews) {
		mReviews.remove(reviews);
		mRatingIsValid = false;
	}

	public CollectionReview getReviews() {
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
	public void setComment(RDComment comment) {
	}

	@Override
	public RDComment getComment() {
		ReviewNodeTraverser traverser = new ReviewNodeTraverser(getReviewNode());
		VisitorCommentCollector collector = new VisitorCommentCollector();
		traverser.setVisitor(collector);
		traverser.traverse();
		return collector.get();
	}

	@Override
	public void deleteComment() {
	}

	@Override
	public boolean hasComment() {
		RDCommentCollection comments = (RDCommentCollection)getComment();
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
	public RDFacts getFacts() {
		return null;
	}

	@Override
	public void setFacts(RDFacts data) {
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
			CollectionReviewNode nodes = new CollectionReviewNode(mReviews);
			for(ReviewNode r : nodes)
				r.acceptVisitor(calculator);
		}

		return calculator.getRating();
	}

}
