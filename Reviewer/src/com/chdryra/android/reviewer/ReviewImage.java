package com.chdryra.android.reviewer;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

public class ReviewImage implements Parcelable{

	private Bitmap mBitmap;
	private String mCaption;
	private LatLng mLatLng;
	
	public ReviewImage(Bitmap bitmap) {
		mBitmap = bitmap;
	}
	
	public ReviewImage(Parcel in) {
		mBitmap = in.readParcelable(Bitmap.class.getClassLoader());
		mCaption = in.readString();
		mLatLng = in.readParcelable(LatLng.class.getClassLoader());
	}

	public Bitmap getBitmap() {
		return mBitmap;
	}
	
	public String getCaption() {
		return mCaption;
	}

	public void setCaption(String caption) {
		mCaption = caption;
	}

	public boolean hasCaption() {
		return mCaption != null;
	}

	public LatLng getLatLng() {
		return mLatLng;
	}

	public void setLatLng(LatLng latLng) {
		mLatLng = latLng;
	}

	public boolean hasLatLng() {
		return mLatLng != null;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(mBitmap, flags);
		dest.writeString(mCaption);
		dest.writeParcelable(mLatLng, flags);
	}
	
	public static final Parcelable.Creator<ReviewImage> CREATOR 
	= new Parcelable.Creator<ReviewImage>() {
	    public ReviewImage createFromParcel(Parcel in) {
	        return new ReviewImage(in);
	    }

	    public ReviewImage[] newArray(int size) {
	        return new ReviewImage[size];
	    }
	};
}
