package com.chdryra.android.reviewer;

import android.graphics.Bitmap;

public class ReviewImage {

	private Bitmap mBitmap;
	private String mCaption;
	
	public ReviewImage(Bitmap bitmap) {
		mBitmap = bitmap;
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
}
