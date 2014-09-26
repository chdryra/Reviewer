/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import com.chdryra.android.mygenerallibrary.SortableList;

public class RDList<T extends RData> extends SortableList<T> implements RData {
	private Review mHoldingReview;
	
	public RDList() {
	}

	public RDList(Review holdingReview) {
		mHoldingReview = holdingReview;
	}

	public RDList(RDList<T> data, Review holdingReview) {
		add(data);
		mHoldingReview = holdingReview;
	}

	@Override
	public void setHoldingReview(Review holdingReview) {
		mHoldingReview = holdingReview;
	}
	
	@Override
	public Review getHoldingReview() {
		return mHoldingReview;
	}
	
	@Override
	public boolean hasData() {
		return mData.size() > 0;
	}
}
