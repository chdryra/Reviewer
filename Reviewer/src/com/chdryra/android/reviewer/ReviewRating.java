package com.chdryra.android.reviewer;

import android.os.Parcel;
import android.os.Parcelable;

public class ReviewRating implements ReviewData {
	private Review mHoldingReview;
	private float mRating;
	
	public ReviewRating(float rating, Review holdingReview) {
		mRating = rating;
		mHoldingReview = holdingReview;
	}
	
	public ReviewRating(Parcel in) {
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
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeFloat(mRating);
	}

	public static final Parcelable.Creator<ReviewRating> CREATOR = new Parcelable.Creator<ReviewRating>() {
	    public ReviewRating createFromParcel(Parcel in) {
	        return new ReviewRating(in);
	    }

	    public ReviewRating[] newArray(int size) {
	        return new ReviewRating[size];
	    }
	};

}
