package com.chdryra.android.reviewer;

import java.util.Map;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class ReviewCommentCollection extends RCollection<ReviewComment> implements ReviewComment{
	private static final String COMMENTS = "Comments"; 
	private static final String DATA = "COMMENTS DATA";

	private Review mHoldingReview;
	
	public ReviewCommentCollection() {
	}

	@SuppressWarnings("unchecked")
	public ReviewCommentCollection(Parcel in) {
		Bundle args = in.readBundle();
		Map<ReviewID, ReviewComment> map = (Map<ReviewID, ReviewComment>) args.getSerializable(DATA);
		mData.putAll(map);
	}

	@Override
	public void setHoldingReview(Review review) {
		mHoldingReview = review;
	}
	
	@Override
	public Review getHoldingReview() {
		return mHoldingReview;
	}
	
	@Override
	public String getCommentTitle() {
		StringBuilder sb = new StringBuilder();
		sb.append(size());
		sb.append(" ");
		sb.append(COMMENTS);
		
		return sb.toString();
	}

	@Override
	public String getCommentString() {
		return toString();
	}

	@Override
	public String toString() {
		
		return super.toString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		Bundle args = new Bundle();
		args.putSerializable(DATA, mData);
		dest.writeBundle(args);
	}
	
	public static final Parcelable.Creator<ReviewCommentCollection> CREATOR 
	= new Parcelable.Creator<ReviewCommentCollection>() {
	    public ReviewCommentCollection createFromParcel(Parcel in) {
	        return new ReviewCommentCollection(in);
	    }

	    public ReviewCommentCollection[] newArray(int size) {
	        return new ReviewCommentCollection[size];
	    }
	};
}
