package com.chdryra.android.reviewer;

import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

public class RDDate implements RData {

	private Review mHoldingReview;
	private Date mDate;

	public RDDate() {
	}

	public RDDate(Date date, Review holdingReview) {
		mDate = date;
		mHoldingReview = holdingReview;
	}

	public Date get() {
		return mDate;
	}
	
	public RDDate(Parcel in) {
		mDate = new Date(in.readLong());
	}

	@Override
	public void setHoldingReview(Review review) {
		mHoldingReview= review;
	}

	@Override
	public Review getHoldingReview() {
		return mHoldingReview;
	}

	@Override
	public boolean hasData() {
		return mDate != null;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(mDate.getTime());
	}

	public static final Parcelable.Creator<RDDate> CREATOR 
	= new Parcelable.Creator<RDDate>() {
	    public RDDate createFromParcel(Parcel in) {
	        return new RDDate(in);
	    }

	    public RDDate[] newArray(int size) {
	        return new RDDate[size];
	    }
	};
}
