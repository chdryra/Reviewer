package com.chdryra.android.reviewer;

import android.os.Parcel;
import android.os.Parcelable;

public class ReviewCollection extends RCollection<Review> implements Parcelable {
	
	public ReviewCollection() {
	}
	
	public void add(Review review) {
		put(review.getID(), review);
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	public ReviewCollection(Parcel in) {
		Parcelable[] reviews = in.readParcelableArray(ReviewNode.class.getClassLoader());
		for(int i = 0; i < reviews.length; ++i) {
			Review review = (ReviewNode)reviews[i];
			mData.put(review.getID(), review);
		}
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		Review[] reviews = mData.values().toArray(new ReviewNode[mData.size()]);
		dest.writeParcelableArray(reviews, flags);
	}
	
	public static final Parcelable.Creator<ReviewCollection> CREATOR 
	= new Parcelable.Creator<ReviewCollection>() {
	    public ReviewCollection createFromParcel(Parcel in) {
	        return new ReviewCollection(in);
	    }

	    public ReviewCollection[] newArray(int size) {
	        return new ReviewCollection[size];
	    }
	};}
