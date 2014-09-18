package com.chdryra.android.reviewer;


public interface VisitorRatingCalculator extends VisitorReviewNode {
	public float getRating();
	public void clear();
}
