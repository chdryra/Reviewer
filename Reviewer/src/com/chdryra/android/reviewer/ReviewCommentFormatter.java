package com.chdryra.android.reviewer;

import java.util.StringTokenizer;

public class ReviewCommentFormatter {
	private static final String COMMENT_HEADLINE_DELIMITER = ".!?";
	
	private ReviewComment mReviewComment;
	
	public ReviewCommentFormatter(ReviewComment reviewComment) {
		mReviewComment = reviewComment;
	}
	
	public String getHeadline() {
		String comment = mReviewComment.getCommentString();
		if(comment != null) {
			StringTokenizer tokens = new StringTokenizer(comment, COMMENT_HEADLINE_DELIMITER);
			return tokens.nextToken();
		} else
			return null;
	}
}
