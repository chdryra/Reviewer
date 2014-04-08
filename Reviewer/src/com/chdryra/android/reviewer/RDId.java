package com.chdryra.android.reviewer;

import java.util.UUID;

import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.Parcelable;

public class RDId implements RData{
	private Review mHoldingReview;
	private ParcelUuid mID;
	
	private RDId(Review holdingReview) {
		mID = new ParcelUuid(UUID.randomUUID());
		mHoldingReview = holdingReview;
	}
	
	public static RDId generateID(Review holdingReview) {
		return new RDId(holdingReview);
	}


	@Override
	public void setHoldingReview(Review review) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Review getHoldingReview() {
		return mHoldingReview;
	}

	@Override
	public boolean hasData() {
		return true;
	}
	
	public boolean equals(RDId rDId) {
		return mID.equals(rDId.mID);
	}
	
	public String toString() {
		return mID.toString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(mID, flags);
	}
	
	public RDId(Parcel in) {
		mID = in.readParcelable(ParcelUuid.class.getClassLoader());
	}
	
	public static final Parcelable.Creator<RDId> CREATOR 
	= new Parcelable.Creator<RDId>() {
	    public RDId createFromParcel(Parcel in) {
	        return new RDId(in);
	    }

	    public RDId[] newArray(int size) {
	        return new RDId[size];
	    }
	};

}