package com.chdryra.android.reviewer;

import java.util.UUID;

import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.Parcelable;

public class RDId implements RData{
	private ParcelUuid mID;
	
	private RDId() {
		mID = new ParcelUuid(UUID.randomUUID());
	}
	
	private RDId(String rDId) {
		mID = ParcelUuid.fromString(rDId);
	}
	
	public static RDId generateID() {
		return new RDId();
	}

	public static RDId generateID(String rDId) {
		return new RDId(rDId);
	}

	@Override
	public void setHoldingReview(Review review) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Review getHoldingReview() {
		return FactoryReview.createNullReview();
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
	public boolean equals(Object obj) {
		if(obj == null || obj.getClass() != getClass())
			return false;
		
		RDId objId = (RDId)obj;
		if(this.mID.equals(objId.mID))
			return true;
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return mID.hashCode();
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