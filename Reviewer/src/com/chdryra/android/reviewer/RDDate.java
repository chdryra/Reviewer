package com.chdryra.android.reviewer;

import java.util.Date;

public class RDDate implements RData {

	private Review mHoldingReview;
	private Date mDate;

	public RDDate() {
	}

	public RDDate(Date date, Review holdingReview) {
		mDate = date;
		mHoldingReview = holdingReview;
	}

	public Date get() {
		return mDate;
	}
	
	@Override
	public void setHoldingReview(Review review) {
		mHoldingReview= review;
	}

	@Override
	public Review getHoldingReview() {
		return mHoldingReview;
	}

	@Override
	public boolean hasData() {
		return mDate != null;
	}
}
