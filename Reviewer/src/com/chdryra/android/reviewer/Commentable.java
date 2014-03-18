package com.chdryra.android.reviewer;

public interface Commentable {
	public abstract void setComment(String comment);
	public abstract String getComment();
	public abstract String getCommentTitle();
	public abstract void deleteComment();
	public abstract boolean hasComment();
}
