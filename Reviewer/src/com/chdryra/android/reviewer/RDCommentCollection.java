package com.chdryra.android.reviewer;

public class RDCommentCollection extends RCollection<RDComment> implements RDComment{
	private static final String COMMENTS = "Comments"; 

	private Review mHoldingReview;
	
	public RDCommentCollection() {
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
		return size() > 0;
	}
	
	@Override
	public String getCommentTitle() {
		StringBuilder sb = new StringBuilder();
		sb.append(size());
		sb.append(" ");
		sb.append(COMMENTS);
		
		return sb.toString();
	}

	@Override
	public String getCommentString() {
		return toString();
	}

	@Override
	public String toString() {
		return getCommentTitle();
	}
}
