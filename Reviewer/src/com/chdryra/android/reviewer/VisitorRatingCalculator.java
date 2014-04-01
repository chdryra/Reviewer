package com.chdryra.android.reviewer;

import android.os.Parcelable;

public interface VisitorRatingCalculator extends VisitorReviewNode, Parcelable {
	public float getRating();
}
