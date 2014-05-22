package com.chdryra.android.reviewer;

public class RDComment implements RData{
	private Review mHoldingReview;
	private String mComment;
	
	public RDComment() {
	}
	
	public RDComment(String comment, Review holdingReview) {
		mComment = comment;
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
		return mComment != null && mComment.length() > 0;
	}
	
	public String get() {
		return mComment;
	}
}
