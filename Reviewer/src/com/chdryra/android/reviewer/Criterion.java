package com.chdryra.android.reviewer;

public class Criterion implements Commentable{
	private String mName;		
	private float mRating;
	private String mComment;
	
	public Criterion() {
	}
	
	public Criterion(String name) {
		mName = name;
	}
	
	public Criterion(String name, float rating) {
		mName = name;
		mRating = rating;
	}

	public void setRating(float rating) {
		mRating = rating;
	}
	
	public float getRating() {
		return mRating;
	}
	
	public void setName(String name) {
		mName = name;
	}
	
	public String getName() {
		return mName;
	}

	@Override
	public String getCommentTitle() {
		return getName();
	}
	
	@Override
	public String getComment() {
		return mComment;
	}

	@Override
	public void setComment(String comment) {
		mComment = comment;
	}

	@Override
	public void deleteComment() {
		mComment = null;
	}
}

