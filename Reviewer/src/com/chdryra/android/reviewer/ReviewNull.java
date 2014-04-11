package com.chdryra.android.reviewer;

import android.os.Parcel;
import android.os.Parcelable;

public class ReviewNull extends ReviewBasic {

	public ReviewNull() {
		super("");
	}
	
	@Override
	public void setRating(float rating) {
	}
	
	@Override
	public void setTitle(String title) {
	}
	
}
