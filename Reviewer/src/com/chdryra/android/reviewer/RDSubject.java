package com.chdryra.android.reviewer;

public class RDSubject implements RData {
	private Review mHoldingReview;
	private String mTitle;
	
	public RDSubject(String title, Review review) {
		mTitle = title;
		mHoldingReview = review;
	}
	
	public RDSubject(RDSubject title, Review review) {
		mTitle = title.toString();
		mHoldingReview = review;
	}
	
	public String get() {
		return mTitle;
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
		return mTitle != null && mTitle.length() > 0;
	}
}
