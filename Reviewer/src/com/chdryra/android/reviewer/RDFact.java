package com.chdryra.android.reviewer;

public class RDFact implements RData{

	private Review mHoldingReview;
	private String mLabel;
	private String mValue;
	
	public RDFact(String label, String value) {	
		mLabel = label;
		mValue = value;			
	}
	
	public RDFact(String label, String value, Review holdingReview) {	
		mLabel = label;
		mValue = value;			
		mHoldingReview = holdingReview;
	}

	public String getLabel() {
		return mLabel;
	}

	public String getValue() {
		return mValue;
	}

	@Override
	public Review getHoldingReview() {
		return mHoldingReview;
	}
	
	@Override
	public void setHoldingReview(Review review) {
		mHoldingReview = review;
	}
	
	@Override
	public boolean hasData() {
		return mLabel != null && mValue != null;
	}
}
