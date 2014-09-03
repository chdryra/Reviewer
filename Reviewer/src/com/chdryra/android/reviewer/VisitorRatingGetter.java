package com.chdryra.android.reviewer;


public class VisitorRatingGetter implements VisitorRatingCalculator {

	private float mRating;
	
	public VisitorRatingGetter() {
	}

	@Override
	public void visit(ReviewNode reviewNode) {
		mRating = reviewNode.getRating().get();
	}


	@Override
	public float getRating() {
		return mRating;
	}

	@Override
	public void clear() {
		mRating = 0;
	}
}
