package com.chdryra.android.reviewer;

import java.util.StringTokenizer;
import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

public class RDLocation implements RData{
	private static final String LOCATION_DELIMITER = ",|.";
	
	private Review mHoldingReview;
	
	private LatLng mLatLng = null;
	private Bitmap mMapSnapshot = null;
	private float mMapSnapshotZoom;
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

	public Bitmap getMapSnapshot() {
		return mMapSnapshot;
	}

	public void setMapSnapshot(Bitmap mapSnapshot, float zoom) {
		mMapSnapshot = mapSnapshot;
		mMapSnapshotZoom = zoom;
	}

	public float getMapSnapshotZoom() {
		return mMapSnapshotZoom;
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
	
	public boolean hasMapSnapshot() {
		return mMapSnapshot != null;
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
