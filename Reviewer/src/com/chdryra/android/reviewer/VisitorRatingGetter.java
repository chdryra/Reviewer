package com.chdryra.android.reviewer;

import android.os.Parcel;
import android.os.Parcelable;

public class VisitorRatingGetter implements VisitorRatingCalculator {

	private float mRating;
	
	public VisitorRatingGetter() {
	}
	
	public VisitorRatingGetter(Parcel in) {
		mRating = in.readFloat();
	}


	@Override
	public void visit(ReviewNode reviewNode) {
		mRating = reviewNode.getRating();
	}


	@Override
	public float getRating() {
		return mRating;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeFloat(mRating);
	}

	public static final Parcelable.Creator<VisitorRatingGetter> CREATOR 
	= new Parcelable.Creator<VisitorRatingGetter>() {
	    public VisitorRatingGetter createFromParcel(Parcel in) {
	        return new VisitorRatingGetter(in);
	    }

	    public VisitorRatingGetter[] newArray(int size) {
	        return new VisitorRatingGetter[size];
	    }
	};
}
