/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

public class RDImage implements RData{
	
	private Review mHoldingReview;
	private Bitmap mBitmap;
	private String mCaption;
	private LatLng mLatLng;
	private boolean mIsCover = false;

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

	public LatLng getLatLng() {
		return mLatLng;
	}

	public boolean isCover() {
		return mIsCover;
	}
}
