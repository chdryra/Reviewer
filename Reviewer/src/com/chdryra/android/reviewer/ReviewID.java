package com.chdryra.android.reviewer;

import java.util.UUID;

import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.Parcelable;

public class ReviewID implements Parcelable{
	private ParcelUuid mID;
	
	private ReviewID() {
		mID = new ParcelUuid(UUID.randomUUID());
	}
	
	public static ReviewID generateID() {
		return new ReviewID();
	}
	
	public boolean equals(ReviewID reviewID) {
		return mID.equals(reviewID.mID);
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
	
	public ReviewID(Parcel in) {
		mID = in.readParcelable(ParcelUuid.class.getClassLoader());
	}
	
	public static final Parcelable.Creator<ReviewID> CREATOR 
	= new Parcelable.Creator<ReviewID>() {
	    public ReviewID createFromParcel(Parcel in) {
	        return new ReviewID(in);
	    }

	    public ReviewID[] newArray(int size) {
	        return new ReviewID[size];
	    }
	};
}