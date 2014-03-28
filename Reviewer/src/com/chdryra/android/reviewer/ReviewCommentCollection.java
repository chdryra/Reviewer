package com.chdryra.android.reviewer;

public class ReviewCommentCollection extends RCollection<ReviewComment> implements ReviewComment{
	private static final String COMMENTS = "Comments"; 
	
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
		
		return super.toString();
	}
}
