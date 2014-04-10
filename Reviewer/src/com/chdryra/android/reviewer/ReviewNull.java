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
	
	public ReviewNull(Parcel in) {
		super(in);
	}
	
	public static final Parcelable.Creator<ReviewNull> CREATOR 
	= new Parcelable.Creator<ReviewNull>() {
	    public ReviewNull createFromParcel(Parcel in) {
	        return new ReviewNull(in);
	    }

	    public ReviewNull[] newArray(int size) {
	        return new ReviewNull[size];
	    }
	};

}
