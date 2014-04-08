package com.chdryra.android.reviewer;

import android.os.Parcel;
import android.os.Parcelable;

public class CollectionReview extends RCollection<Review> implements Parcelable {
	
	public CollectionReview() {
	}
	
	public void add(Review review) {
		put(review.getID(), review);
	}

	public void remove(CollectionReview reviews) {
		for(Review r : reviews)
			remove(r.getID());
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	public CollectionReview(Parcel in) {
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
	
	public static final Parcelable.Creator<CollectionReview> CREATOR 
	= new Parcelable.Creator<CollectionReview>() {
	    public CollectionReview createFromParcel(Parcel in) {
	        return new CollectionReview(in);
	    }

	    public CollectionReview[] newArray(int size) {
	        return new CollectionReview[size];
	    }
	};}
