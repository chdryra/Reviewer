package com.chdryra.android.reviewer;

import android.os.Parcel;

public class VisitorRatingAverager implements VisitorRatingCalculator{
	private float mRatingTotal = 0;
	private int mNumberRatings = 0;
	
	public VisitorRatingAverager(Parcel in) {
		mRatingTotal = in.readFloat();
		mNumberRatings = in.readInt();
	}

	public VisitorRatingAverager() {
	}

	@Override
	public void visit(ReviewNode reviewNode) {
		mRatingTotal += reviewNode.getRating().get();
		mNumberRatings++;
	}

	@Override
	public float getRating() {
		if(mNumberRatings > 0)
			return mRatingTotal / mNumberRatings;
		else
			return 0;
	}

	@Override
	public void clear() {
		mRatingTotal = 0;
		mNumberRatings = 0;
	}

}
