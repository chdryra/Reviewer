package com.chdryra.android.reviewer;

import android.os.Parcel;
import android.os.Parcelable;

public class ReviewTitle implements ReviewData {
	private Review mHoldingReview;
	private String mTitle;
	
	public ReviewTitle(String title, Review review) {
		mTitle = title;
		mHoldingReview = review;
	}
	
	public ReviewTitle(Parcel in) {
		mTitle = in.readString();
	}

	public String get() {
		return mTitle;
	}

	public void set(String title) {
		mTitle = title;
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
		dest.writeString(mTitle);
	}

	public static final Parcelable.Creator<ReviewTitle> CREATOR = new Parcelable.Creator<ReviewTitle>() {
	    public ReviewTitle createFromParcel(Parcel in) {
	        return new ReviewTitle(in);
	    }

	    public ReviewTitle[] newArray(int size) {
	        return new ReviewTitle[size];
	    }
	};
}
