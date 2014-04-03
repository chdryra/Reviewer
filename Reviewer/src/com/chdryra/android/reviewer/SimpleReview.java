package com.chdryra.android.reviewer;

import java.net.URL;

import android.os.Parcel;
import android.os.Parcelable;

public class SimpleReview implements Review {
	private static final String TAG = "SimpleReview";

	private ReviewID mID;
	private ReviewTitle mTitle;
	private ReviewRating mRating;

	public SimpleReview(String title) {
		mID = ReviewID.generateID();
		mTitle = new ReviewTitle(title, this);
		mRating = new ReviewRating(0, this);
	}
	
	public SimpleReview(Parcel in) {
		mID = in.readParcelable(ReviewID.class.getClassLoader());
		mTitle = in.readParcelable(ReviewTitle.class.getClassLoader());
		mTitle.setHoldingReview(this);
		mRating = in.readParcelable(ReviewRating.class.getClassLoader());
		mRating.setHoldingReview(this);
	}

	@Override
	public ReviewID getID() {
		return mID;
	}

	@Override
	public ReviewTitle getTitle() {
		return mTitle;
	}

	@Override
	public void setTitle(String title) {
		mTitle.set(title);
	}

	@Override
	public ReviewRating getRating() {
		return mRating;
	}

	@Override
	public void setRating(float rating) {
		mRating.set(rating);
	}

	@Override
	public void setComment(ReviewComment comment) {
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
	}

	@Override
	public void deleteFacts() {
	}

	@Override
	public boolean hasFacts() {
		return false;
	}

	@Override
	public URL getURL() {
		return null;
	}

	@Override
	public void setURL(URL url) {
	}

	@Override
	public void deleteURL() {
	}

	@Override
	public boolean hasURL() {
		return false;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(mID, flags);
		dest.writeParcelable(mTitle, flags);
		dest.writeParcelable(mRating, flags);
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
