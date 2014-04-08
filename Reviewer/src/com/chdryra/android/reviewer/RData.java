package com.chdryra.android.reviewer;

import android.os.Parcelable;

public interface RData extends Parcelable {
	public void setHoldingReview(Review review);
	public Review getHoldingReview();
	public boolean hasData();
}
