package com.chdryra.android.reviewer;

import android.os.Parcel;
import android.os.Parcelable;

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
		mRatingTotal += reviewNode.getRating();
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
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeFloat(mRatingTotal);
		dest.writeInt(mNumberRatings);
	}
	
	public static final Parcelable.Creator<VisitorRatingAverager> CREATOR 
	= new Parcelable.Creator<VisitorRatingAverager>() {
	    public VisitorRatingAverager createFromParcel(Parcel in) {
	        return new VisitorRatingAverager(in);
	    }

	    public VisitorRatingAverager[] newArray(int size) {
	        return new VisitorRatingAverager[size];
	    }
	};

}
