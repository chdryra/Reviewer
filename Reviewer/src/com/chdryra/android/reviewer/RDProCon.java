package com.chdryra.android.reviewer;

public class RDProCon implements RData {

	private Review mHoldingReview;
	private String mProCon;
	private boolean mIsPro = true;
	
	public RDProCon(String proCon, boolean isPro, Review holdingReview) {
		mHoldingReview = holdingReview;
		mProCon = proCon;
		mIsPro = isPro;
	}
	
	public String getProCon() {
		return mProCon;
	}

	public boolean isPro() {
		return mIsPro;
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
		return mProCon != null && mProCon.length() > 0;
	}

}
