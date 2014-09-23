/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

public class RDRating implements RData {
	private Review mHoldingReview;
	private float mRating;
	
	public RDRating(float rating, Review holdingReview) {
		mRating = rating;
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
