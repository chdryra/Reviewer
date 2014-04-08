package com.chdryra.android.reviewer;

import java.util.StringTokenizer;

public class CommentFormatter {
	private static final String COMMENT_HEADLINE_DELIMITER = ".!?";
	
	private RDComment mRDComment;
	
	public CommentFormatter(RDComment rDComment) {
		mRDComment = rDComment;
	}
	
	public String getHeadline() {
		String comment = mRDComment.getCommentString();
		if(comment != null && comment.length() > 0) {
			StringTokenizer tokens = new StringTokenizer(comment, COMMENT_HEADLINE_DELIMITER);
			return tokens.nextToken();
		} else
			return null;
	}
}
