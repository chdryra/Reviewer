package com.chdryra.android.reviewer;

public class SingleComment implements ReviewComment{
	private String mTitle;
	private String mComment;
	
	private SingleComment(String title, String comment) {
		mTitle = title;
		mComment = comment;
	}
	
	public String getCommentTitle() {
		return mTitle;
	}
	
	public String getCommentString() {
		return mComment;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(mTitle);
		sb.append(": ");
		sb.append(mComment);
		
		return sb.toString();
	}
}
