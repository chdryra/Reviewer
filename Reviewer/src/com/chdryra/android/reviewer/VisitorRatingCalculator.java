package com.chdryra.android.reviewer;

import android.os.Parcelable;

public interface VisitorRatingCalculator extends ReviewNodeVisitor, Parcelable {
	public float getRating();
}
