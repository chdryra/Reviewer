package com.chdryra.android.reviewer;

import android.os.Parcel;
import android.os.Parcelable;

public class RDCommentSingle implements RDComment{
	private static final String DEFAULT_TITLE = "Comment";
	
	private Review mHoldingReview;
	private String mComment;
	
	public RDCommentSingle() {
	}
	
	public RDCommentSingle(String comment) {
		mComment = comment;
	}
	
	public RDCommentSingle(Parcel in) {
		mComment = in.readString();
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
	public boolean hasData() {
		return mComment != null && mComment.length() > 0;
	}
	
	public String getCommentTitle() {
		return mHoldingReview == null? DEFAULT_TITLE : mHoldingReview.getTitle().get();
	}
	
	public String getCommentString() {
		return mComment;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getCommentTitle());
		sb.append(": ");
		sb.append(mComment);
		
		return sb.toString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mComment);
	}
	
	public static final Parcelable.Creator<RDCommentSingle> CREATOR 
	= new Parcelable.Creator<RDCommentSingle>() {
	    public RDCommentSingle createFromParcel(Parcel in) {
	        return new RDCommentSingle(in);
	    }

	    public RDCommentSingle[] newArray(int size) {
	        return new RDCommentSingle[size];
	    }
	};
}
