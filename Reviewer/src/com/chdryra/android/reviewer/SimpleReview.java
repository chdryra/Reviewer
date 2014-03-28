package com.chdryra.android.reviewer;

import com.chdryra.android.reviewer.ReviewIDGenerator.ReviewID;

public class SimpleReview extends AbstractReview{
	private ReviewID mID = ReviewIDGenerator.generateID();
	private String mTitle;		
	private float mRating;
	
	public SimpleReview(String title) {
		mTitle = title;
	}
	
	@Override
	public ReviewID getID() {
		return mID;
	}
	
	@Override
	public void setRating(float rating) {
		mRating = rating;
	}
	
	@Override
	public float getRating() {
		return mRating;
	}
	
	@Override
	public void setTitle(String title) {
		mTitle = title;
	}
	
	@Override
	public String getTitle() {
		return mTitle;
	}
	@Override
	public void acceptVisitor(ReviewVisitor reviewVisitor) {
		reviewVisitor.visit(this);
	}
}

