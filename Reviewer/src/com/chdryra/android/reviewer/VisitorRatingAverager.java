package com.chdryra.android.reviewer;

public class VisitorRatingAverager implements ReviewVisitor{
	private float mRatingTotal = 0;
	private int mNumberRatings = 0;
	
	@Override
	public void visit(Review review) {
		mRatingTotal += review.getRating();
		mNumberRatings++;
	}

	public float getAverage() {
		if(mNumberRatings > 0)
			return mRatingTotal / mNumberRatings;
		else
			return 0;
	}
}
