package com.chdryra.android.reviewer;

import java.util.LinkedHashMap;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class ReviewNodeCollection extends RCollection<ReviewNode> implements Parcelable{
	private static final String REVIEWS = "REVIEWS";
	private static final String DATA = "REVIEW COLLECTION DATA";
	
	private ReviewID mID = ReviewID.generateID();
	private float mAverageRating;
	private boolean mValidAverage = false;
	
	@SuppressWarnings("unchecked")
	public ReviewNodeCollection(Parcel in) {
		mID = new ReviewID(in);
		mAverageRating = in.readFloat();
		mValidAverage = in.readByte() != 0;
		Bundle args = in.readBundle();
		mData = (LinkedHashMap<ReviewID, ReviewNode>) args.getSerializable(DATA);
	}

	public ReviewNodeCollection() {
	}

//	//Review methods
//	@Override
//	public ReviewID getID() {
//		return mID;
//	}
//
//	@Override
//	public float getRating() {
//		return getAverageRating();
//	}
//	
//	@Override
//	public String getTitle() {
//		StringBuilder sb = new StringBuilder();
//		sb.append(size());
//		sb.append(" ");
//		sb.append(REVIEWS);
//		
//		return sb.toString();
//	}
//	
//	@Override
//	public void setRating(float rating) {
//		throw new UnsupportedOperationException();
//	}
//
//	@Override
//	public void setTitle(String title) {
//		throw new UnsupportedOperationException();
//	}
//	
//	@Override
//	public void setComment(ReviewComment comment) {
//		throw new UnsupportedOperationException();
//	}
//
//	@Override
//	public ReviewComment getComment() {
//		return null;
//	}
//
//	@Override
//	public void deleteComment() {
//	}
//
//	@Override
//	public boolean hasComment() {
//		return false;
//	}
//
//	@Override
//	public ReviewImage getImage() {
//		return null;
//	}
//
//	@Override
//	public void setImage(ReviewImage image) {
//		throw new UnsupportedOperationException();
//	}
//
//	@Override
//	public void deleteImage() {
//		throw new UnsupportedOperationException();
//	}
//
//	@Override
//	public boolean hasImage() {
//		throw new UnsupportedOperationException();
//	}
//
//	@Override
//	public ReviewLocation getLocation() {
//		return null;
//	}
//
//	@Override
//	public void setLocation(ReviewLocation location) {
//		throw new UnsupportedOperationException();
//	}
//
//	@Override
//	public void deleteLocation() {
//		throw new UnsupportedOperationException();
//	}
//
//	@Override
//	public boolean hasLocation() {
//		return false;
//	}
//
//	@Override
//	public ReviewFacts getFacts() {
//		return null;
//	}
//
//	@Override
//	public void setFacts(ReviewFacts data) {
//		throw new UnsupportedOperationException();
//	}
//
//	@Override
//	public void deleteFacts() {
//		throw new UnsupportedOperationException();
//	}
//
//	@Override
//	public boolean hasFacts() {
//		return false;
//	}
//	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(mID, flags);
		dest.writeFloat(mAverageRating);
		dest.writeByte((byte) (mValidAverage ? 1 : 0));
		Bundle args = new Bundle();
		args.putSerializable(DATA, mData);
	}
	
	public static final Parcelable.Creator<ReviewNodeCollection> CREATOR 
	= new Parcelable.Creator<ReviewNodeCollection>() {
	    public ReviewNodeCollection createFromParcel(Parcel in) {
	        return new ReviewNodeCollection(in);
	    }

	    public ReviewNodeCollection[] newArray(int size) {
	        return new ReviewNodeCollection[size];
	    }
	};
}	