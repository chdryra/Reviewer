/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import java.net.URL;

public class RDUrl implements RData {
	private Review mHoldingReview;
	private final URL mURL;

	public RDUrl(URL url, Review holdingReview) {
		mURL = url;
		mHoldingReview = holdingReview;
	}

	public URL get() {
		return mURL;
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
		return mURL != null;
	}
}
