package com.chdryra.android.reviewer;

import android.os.Parcel;
import android.os.Parcelable;

public class RDRating implements RData {
	private Review mHoldingReview;
	private float mRating;
	
	public RDRating(float rating, Review holdingReview) {
		mRating = rating;
		mHoldingReview = holdingReview;
	}
	
	public RDRating(Parcel in) {
		mRating = in.readFloat();
	}

	public float get() {
		return mRating;
	}

	public void set(float rating) {
		mRating = rating;
	}
	
	@Override
	public void setHoldingReview(Review review) {
		mHoldingReview = review;
	}

	@Override
	public Review getHoldingReview() {
		return mHoldingReview;
	}
	
	@Override
	public boolean hasData() {
		return true ;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeFloat(mRating);
	}

	public static final Parcelable.Creator<RDRating> CREATOR = new Parcelable.Creator<RDRating>() {
	    public RDRating createFromParcel(Parcel in) {
	        return new RDRating(in);
	    }

	    public RDRating[] newArray(int size) {
	        return new RDRating[size];
	    }
	};

}
