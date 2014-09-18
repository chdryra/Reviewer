package com.chdryra.android.reviewer;

import java.util.LinkedList;
import java.util.StringTokenizer;

public class CommentFormatter {
	private static final String COMMENT_HEADLINE_DELIMITER = ".!?";
	
	private RDComment mRDComment;
	
	public CommentFormatter(RDComment rDComment) {
		mRDComment = rDComment;
	}
	
	public String getHeadline() {
		return getHeadline(mRDComment.get());
	}
	
	public static String getHeadline(String comment) {
		if(comment != null && comment.length() > 0) {
			StringTokenizer tokens = new StringTokenizer(comment, COMMENT_HEADLINE_DELIMITER);
			return tokens.nextToken();
		} else
			return null;
	}
	
	public static LinkedList<String> split(String comment) {
		LinkedList<String> comments = new LinkedList<String>();
		
		if(comment != null && comment.length() > 0) {
			StringTokenizer tokens = new StringTokenizer(comment, COMMENT_HEADLINE_DELIMITER);
			while (tokens.hasMoreTokens()) {
				String sentence = tokens.nextToken().trim();
				if(sentence.length() > 0)
					comments.add(sentence);
			}
		}
		
		return comments;
	}
}
