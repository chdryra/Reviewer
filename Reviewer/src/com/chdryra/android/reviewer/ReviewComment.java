package com.chdryra.android.reviewer;

import android.os.Parcelable;

public interface ReviewComment extends Parcelable{
	public String getCommentTitle();
	public String getCommentString();
	public String toString();
}
