package com.chdryra.android.reviewer;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

public class RDImage implements RData{
	
	private Review mHoldingReview;
	
	private Bitmap mBitmap;
	private String mCaption;
	private LatLng mLatLng;


	public RDImage() {
	}

	public RDImage(Bitmap bitmap, Review holdingReview) {
		mBitmap = bitmap;
		mHoldingReview = holdingReview;
	}
	
	public RDImage(Parcel in) {
		mBitmap = in.readParcelable(Bitmap.class.getClassLoader());
		mCaption = in.readString();
		mLatLng = in.readParcelable(LatLng.class.getClassLoader());
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
		return mBitmap != null;
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
	
	public static final Parcelable.Creator<RDImage> CREATOR 
	= new Parcelable.Creator<RDImage>() {
	    public RDImage createFromParcel(Parcel in) {
	        return new RDImage(in);
	    }

	    public RDImage[] newArray(int size) {
	        return new RDImage[size];
	    }
	};
}
