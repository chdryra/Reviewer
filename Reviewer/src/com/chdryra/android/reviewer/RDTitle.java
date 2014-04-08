package com.chdryra.android.reviewer;

import android.os.Parcel;
import android.os.Parcelable;

public class RDTitle implements RData {
	private Review mHoldingReview;
	private String mTitle;
	
	public RDTitle(String title, Review review) {
		mTitle = title;
		mHoldingReview = review;
	}
	
	public RDTitle(Parcel in) {
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
	public boolean hasData() {
		return mTitle != null && mTitle.length() > 0;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mTitle);
	}

	public static final Parcelable.Creator<RDTitle> CREATOR = new Parcelable.Creator<RDTitle>() {
	    public RDTitle createFromParcel(Parcel in) {
	        return new RDTitle(in);
	    }

	    public RDTitle[] newArray(int size) {
	        return new RDTitle[size];
	    }
	};
}
