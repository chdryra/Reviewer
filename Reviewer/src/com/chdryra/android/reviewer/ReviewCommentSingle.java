package com.chdryra.android.reviewer;

import android.os.Parcel;
import android.os.Parcelable;

public class ReviewCommentSingle implements ReviewComment{
	private String mTitle;
	private String mComment;
	
	public ReviewCommentSingle(String title, String comment) {
		mTitle = title;
		mComment = comment;
	}
	
	public ReviewCommentSingle(Parcel in) {
		mTitle = in.readString();
		mComment = in.readString();
	}

	public String getCommentTitle() {
		return mTitle;
	}
	
	public String getCommentString() {
		return mComment;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(mTitle);
		sb.append(": ");
		sb.append(mComment);
		
		return sb.toString();
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mTitle);
		dest.writeString(mComment);
	}
	
	public static final Parcelable.Creator<ReviewCommentSingle> CREATOR 
	= new Parcelable.Creator<ReviewCommentSingle>() {
	    public ReviewCommentSingle createFromParcel(Parcel in) {
	        return new ReviewCommentSingle(in);
	    }

	    public ReviewCommentSingle[] newArray(int size) {
	        return new ReviewCommentSingle[size];
	    }
	};
}
