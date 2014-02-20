package com.chdryra.android.reviewer;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

public class NearbySearchFetcher extends GooglePlacesAPIFetcher {
	private static final String TAG = "NearbySearchFetcher";
	
	private static final String NEARBY_SEARCH = "/nearbysearch";
	private static final String RESULTS_TAG = "results";
	public static enum RadiusOrRank{RADIUS, RANK_BY_DISTANCE};
	
	private RadiusOrRank mRadiusOrRank;
	private int mRadius;
	private String mKeyword;
	private LatLng mLatLng;
	
	public NearbySearchFetcher(LatLng latlng, RadiusOrRank radiusOrRank, int radius, String keyword) {
		super(NEARBY_SEARCH, RESULTS_TAG);
		mLatLng = latlng;
		mRadiusOrRank = radiusOrRank;
		if(mRadiusOrRank == RadiusOrRank.RADIUS)
			mRadius = radius;
		mKeyword = keyword;
	}
    	
	@Override
	public String getURLString() {
		StringBuilder urlString = getURLStem();
		urlString.append("&location=" + mLatLng.latitude + "," + mLatLng.longitude);
		urlString.append("&keyword=" + mKeyword);
		
		switch (mRadiusOrRank) {
		case RADIUS:
			urlString.append("&radius=" + mRadius);
			break;
		case RANK_BY_DISTANCE:
			urlString.append("&rankby=distance");
			break;
		default:
			break;
		}
	
		return urlString.toString();
	}	
}