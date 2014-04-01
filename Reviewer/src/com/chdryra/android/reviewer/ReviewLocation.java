package com.chdryra.android.reviewer;

import java.util.StringTokenizer;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

public class ReviewLocation implements Parcelable{
	private static final String LOCATION_DELIMITER = ",|.";
	
	private LatLng mLatLng = null;
	private Bitmap mMapSnapshot = null;
	private float mMapSnapshotZoom;
	private String mName = null;
	
	public ReviewLocation(LatLng latLng) {
		mLatLng = latLng;
	}

	public ReviewLocation(LatLng latLng, String name) {
		mLatLng = latLng;
		setName(name);
	}

	public ReviewLocation(Parcel in) {
		mLatLng = in.readParcelable(LatLng.class.getClassLoader());
		mMapSnapshot = in.readParcelable(Bitmap.class.getClassLoader());
		mMapSnapshotZoom = in.readFloat();
		mName = in.readString();
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

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(mLatLng, flags);
		dest.writeParcelable(mMapSnapshot, flags);
		dest.writeFloat(mMapSnapshotZoom);
		dest.writeString(mName);
	}

	public static final Parcelable.Creator<ReviewLocation> CREATOR 
	= new Parcelable.Creator<ReviewLocation>() {
	    public ReviewLocation createFromParcel(Parcel in) {
	        return new ReviewLocation(in);
	    }

	    public ReviewLocation[] newArray(int size) {
	        return new ReviewLocation[size];
	    }
	};	
}
