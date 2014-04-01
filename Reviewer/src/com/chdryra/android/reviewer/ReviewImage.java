package com.chdryra.android.reviewer;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class ReviewImage implements Parcelable{

	private Bitmap mBitmap = null;
	private String mCaption = null;
	
	public ReviewImage(Bitmap bitmap) {
		mBitmap = bitmap;
	}
	
	public ReviewImage(Parcel in) {
		mBitmap = in.readParcelable(Bitmap.class.getClassLoader());
		mCaption = in.readString();
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

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(mBitmap, flags);
		dest.writeString(mCaption);
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
