package com.chdryra.android.reviewer;

import com.google.android.gms.maps.model.LatLng;

public class NearbySearchFetcher extends GooglePlacesAPIFetcher {
	private static final String NEARBY_SEARCH = "/nearbysearch";
	private static final String RESULTS_TAG = "results";
	public static enum RadiusOrRank{RADIUS, RANK_BY_DISTANCE, RANK_BY_PROMINENCE};
	
	private RadiusOrRank mRadiusOrRank;
	private int mRadius = 100;
	private String mTypes;
	private LatLng mLatLng;
	
	public NearbySearchFetcher(LatLng latlng, RadiusOrRank radiusOrRank, int radius, String types) {
		super(NEARBY_SEARCH, RESULTS_TAG);
		mLatLng = latlng;
		mRadiusOrRank = radiusOrRank;
		if(mRadiusOrRank == RadiusOrRank.RADIUS || mRadiusOrRank == RadiusOrRank.RANK_BY_PROMINENCE)
			mRadius = radius;
		mTypes = types;
	}
    	
	@Override
	public String getURLString() {
		StringBuilder urlString = getURLStem();
		urlString.append("&location=" + mLatLng.latitude + "," + mLatLng.longitude);
		
		switch (mRadiusOrRank) {
		case RADIUS:
			urlString.append("&radius=" + mRadius);
			break;
		case RANK_BY_DISTANCE:
			urlString.append("&rankby=distance");
			break;
		case RANK_BY_PROMINENCE:
			urlString.append("&radius=" + mRadius);
			urlString.append("&rankby=prominence");
			break;
		default:
			break;
		}
	
		if(mTypes != null)
			urlString.append("&types=" + mTypes);
		
		return urlString.toString();
	}	
}