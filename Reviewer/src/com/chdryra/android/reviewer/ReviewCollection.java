package com.chdryra.android.reviewer;

import com.chdryra.android.reviewer.ReviewIDGenerator.ReviewID;

public class ReviewCollection extends RCollection<Review> implements Review {
	private static final String REVIEWS = "REVIEWS";
	private ReviewID mID;
	private float mAverageRating;
	private boolean mValidAverage = false;
	
	public ReviewCollection() {
		super();
		mID = ReviewIDGenerator.generateID();
	}

	//Review methods
	@Override
	public ReviewID getID() {
		return mID;
	}

	@Override
	public float getRating() {
		return getAverageRating();
	}
	
	@Override
	public String getTitle() {
		StringBuilder sb = new StringBuilder();
		sb.append(size());
		sb.append(" ");
		sb.append(REVIEWS);
		
		return sb.toString();
	}
	
	@Override
	public void setRating(float rating) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setTitle(String title) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void setComment(ReviewComment comment) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ReviewComment getComment() {
		return null;
	}

	@Override
	public void deleteComment() {
	}

	@Override
	public boolean hasComment() {
		return false;
	}

	@Override
	public ReviewImage getImage() {
		return null;
	}

	@Override
	public void setImage(ReviewImage image) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteImage() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasImage() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ReviewLocation getLocation() {
		return null;
	}

	@Override
	public void setLocation(ReviewLocation location) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteLocation() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasLocation() {
		return false;
	}

	@Override
	public ReviewFacts getFacts() {
		return null;
	}

	@Override
	public void setFacts(ReviewFacts data) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteFacts() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasFacts() {
		return false;
	}

	@Override
	public void acceptVisitor(ReviewVisitor reviewVisitor) {
		for(Review r: this)
			r.acceptVisitor(reviewVisitor);		
	}
	
	//RCollection methods
	public void add(Review review) {
		ReviewID id = review.getID();
  		if(!containsID(id)) {
  			mData.put(id, review);
  			invalidAverage();
		}      		
	}

	@Override
	public void remove(ReviewID id) {
		if(containsID(id)) {
			mData.remove(id);
			invalidAverage();
		} 
	}

	//Custom methods
	private void invalidAverage() {
		mValidAverage = false;
	}
	
	private void validAverage() {
		mValidAverage = true;
	}
	
	private float getAverageRating() {
		if(!mValidAverage) {
			VisitorRatingAverager averager = new VisitorRatingAverager();
			acceptVisitor(averager);
			mAverageRating = averager.getAverage();
			validAverage();
		}
		
		return mAverageRating;		
	}
}	