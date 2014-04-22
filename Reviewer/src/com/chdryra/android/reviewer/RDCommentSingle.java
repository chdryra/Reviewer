package com.chdryra.android.reviewer;

public class RDCommentSingle implements RDComment{
	private static final String DEFAULT_TITLE = "Comment";
	
	private Review mHoldingReview;
	private String mComment;
	
	public RDCommentSingle() {
	}
	
	public RDCommentSingle(String comment, Review holdingReview) {
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
	
	public String getCommentTitle() {
		return mHoldingReview == null? DEFAULT_TITLE : mHoldingReview.getTitle().get();
	}
	
	public String getCommentString() {
		return mComment;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getCommentTitle());
		sb.append(": ");
		sb.append(mComment);
		
		return sb.toString();
	}
}
