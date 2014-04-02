package com.chdryra.android.reviewer;

import java.util.LinkedHashMap;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class ReviewNodeCollection extends RCollection<ReviewNode> implements Parcelable {
	private static final String REVIEWS = "REVIEWS";
	private static final String DATA = "REVIEW COLLECTION DATA";
	
	@SuppressWarnings("unchecked")
	public ReviewNodeCollection(Parcel in) {
		Bundle args = in.readBundle();
		mData = (LinkedHashMap<ReviewID, ReviewNode>) args.getSerializable(DATA);
	}

	public ReviewNodeCollection() {
	}
	
	public void add(ReviewNode reviewNode) {
		put(reviewNode.getID(), reviewNode);
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
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