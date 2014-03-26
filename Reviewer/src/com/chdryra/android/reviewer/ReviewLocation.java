package com.chdryra.android.reviewer;

import java.util.StringTokenizer;
import android.graphics.Bitmap;
import com.google.android.gms.maps.model.LatLng;

public class ReviewLocation {
	private static final String LOCATION_DELIMITER = ",|.";
	private LatLng mLatLng;
	
	private Bitmap mMapSnapshot = null;
	private float mMapSnapshotZoom;
	private String mName = null;
	
	private ReviewLocation() {
	}
	
	public ReviewLocation(LatLng latLng) {
		mLatLng = latLng;
	}

	public ReviewLocation(LatLng latLng, String name) {
		mLatLng = latLng;
		setName(name);
	}

	public static ReviewLocation getNullLocation() {
		return new ReviewLocation();
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
