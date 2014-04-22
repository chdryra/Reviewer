package com.chdryra.android.reviewer;

public class RDRating implements RData {
	private Review mHoldingReview;
	private float mRating;
	
	public RDRating(float rating, Review holdingReview) {
		mRating = rating;
		mHoldingReview = holdingReview;
	}
	
	public float get() {
		return mRating;
	}

	public void set(float rating) {
		mRating = rating;
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
