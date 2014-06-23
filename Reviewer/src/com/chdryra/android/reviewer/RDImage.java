package com.chdryra.android.reviewer;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

public class RDImage implements RData{
	
	private Review mHoldingReview;
	private Bitmap mBitmap;
	private String mCaption;
	private LatLng mLatLng;
	private boolean mIsCover = false;

	public RDImage() {
	}

	public RDImage(Bitmap bitmap, LatLng latLng, String caption, boolean isCover, Review holdingReview) {
		mBitmap = bitmap;
		mLatLng = latLng;
		mCaption = caption;
		mIsCover = isCover;
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
	
	public boolean isCover() {
		return mIsCover;
	}
}
