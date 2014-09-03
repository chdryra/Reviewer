package com.chdryra.android.reviewer;

public class RDRating implements RData {
	private Review mHoldingReview;
	private float mRating;
	
	public RDRating(float rating, Review holdingReview) {
		mRating = rating;
		mHoldingReview = holdingReview;
	}
	
	public RDRating(RDRating rating, Review holdingReview) {
		mRating = rating.get();
		mHoldingReview = holdingReview;
	}
	
	public void set(float rating) {
		mRating = rating;
	}
	
	public float get() {
		return mRating;
	}
	
	@Override
	public void setHoldingReview(Review review) {
		mHoldingReview = review;
	}

	@Override
	public Review getHoldingReview() {
		return mHoldingReview;
	}
	
	@Override
	public boolean hasData() {
		return true ;
	}
}
