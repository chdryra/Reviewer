package com.chdryra.android.reviewer;

import android.os.Parcel;
import android.os.Parcelable;

public class SimpleReview implements Review {
	private static final String TAG = "SimpleReview";

	private ReviewID mID;
	private String mTitle;
	private float mRating;

	public SimpleReview(String title) {
		mID = ReviewID.generateID();
		mTitle = title;
	}
	
	public SimpleReview(Parcel in) {
		mID = in.readParcelable(ReviewID.class.getClassLoader());
		mTitle = in.readString();
		mRating = in.readFloat();
	}

	@Override
	public ReviewID getID() {
		return mID;
	}

	@Override
	public String getTitle() {
		return mTitle;
	}

	@Override
	public void setTitle(String title) {
		mTitle = title;
	}

	@Override
	public float getRating() {
		return mRating;
	}

	@Override
	public void setRating(float rating) {
		mRating = rating;
	}

	@Override
	public void setComment(ReviewComment comment) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ReviewComment getComment() {
		return null;
	}

	@Override
	public void deleteComment() {
	}

	@Override
	public boolean hasComment() {
		return false;
	}

	@Override
	public ReviewImage getImage() {
		return null;
	}

	@Override
	public void setImage(ReviewImage image) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteImage() {
	}

	@Override
	public boolean hasImage() {
		return false;
	}

	@Override
	public ReviewLocation getLocation() {
		return null;
	}

	@Override
	public void setLocation(ReviewLocation location) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteLocation() {
	}

	@Override
	public boolean hasLocation() {
		return false;
	}

	@Override
	public ReviewFacts getFacts() {
		return null;
	}

	@Override
	public void setFacts(ReviewFacts facts) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteFacts() {
	}

	@Override
	public boolean hasFacts() {
		return false;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(mID, flags);
		dest.writeString(mTitle);
		dest.writeFloat(mRating);
	}
	
	public static final Parcelable.Creator<SimpleReview> CREATOR 
	= new Parcelable.Creator<SimpleReview>() {
	    public SimpleReview createFromParcel(Parcel in) {
	        return new SimpleReview(in);
	    }

	    public SimpleReview[] newArray(int size) {
	        return new SimpleReview[size];
	    }
	};
}
