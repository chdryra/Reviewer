package com.chdryra.android.reviewer;

import java.util.StringTokenizer;

import com.google.android.gms.maps.model.LatLng;

public class RDLocation implements RData{
	private static final String LOCATION_DELIMITER = ",|";
	
	private Review mHoldingReview;
	
	private LatLng mLatLng = null;
	private String mName = null;

	public RDLocation() {
	}

	public RDLocation(LatLng latLng, Review holdingReview) {
		mLatLng = latLng;
		mHoldingReview = holdingReview;
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
		return mLatLng != null;
	}
	
	public LatLng getLatLng() {
		return mLatLng;
	}

	public String getName() {
		return mName;
	}

	public void setName(String locationName) {
		if(locationName != null && locationName.length() > 0)
			mName = locationName;
		else
			mName = null;
	}
		
	public boolean hasName() {
		return mName != null;
	}
	
	public String getShortenedName() {
		if(mName != null) {
			StringTokenizer tokens = new StringTokenizer(mName, LOCATION_DELIMITER);
			String shortened = tokens.nextToken();
			return shortened != null? shortened.trim() : shortened;
		} else
			return null;
	}
}
