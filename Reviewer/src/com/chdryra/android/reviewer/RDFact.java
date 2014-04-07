package com.chdryra.android.reviewer;

import android.os.Parcel;
import android.os.Parcelable;

public class RDFact implements Parcelable, ReviewData{

	private Review mHoldingReview;
	private String mLabel;
	private String mValue;
	
	public RDFact(String label, String value) {	
		mLabel = label;
		mValue = value;			
	}
	
	public RDFact(String label, String value, Review holdingReview) {	
		mLabel = label;
		mValue = value;			
		mHoldingReview = holdingReview;
	}
	
	public RDFact(Parcel in) {
		mLabel = in.readString();
		mValue = in.readString();
	}

	public String getLabel() {
		return mLabel;
	}

	public String getValue() {
		return mValue;
	}

	@Override
	public Review getHoldingReview() {
		return mHoldingReview;
	}
	
	@Override
	public void setHoldingReview(Review review) {
		mHoldingReview = review;
	}
	
	@Override
	public boolean hasData() {
		return mLabel != null && mValue != null;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mLabel);
		dest.writeString(mValue);
	}

	public static final Parcelable.Creator<RDFact> CREATOR = new Parcelable.Creator<RDFact>() {
	    public RDFact createFromParcel(Parcel in) {
	        return new RDFact(in);
	    }

	    public RDFact[] newArray(int size) {
	        return new RDFact[size];
	    }
	};

}
