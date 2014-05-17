package com.chdryra.android.reviewer;

import java.util.UUID;

import android.os.ParcelUuid;

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
	
}