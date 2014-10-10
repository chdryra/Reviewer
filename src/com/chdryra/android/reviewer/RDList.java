/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import com.chdryra.android.mygenerallibrary.SortableList;

/**
 * Review Data: Sortable collection of RData objects that itself is considered Review Data.
 * <p/>
 * <p>
 * <code>hasData()</code>: at least 1 object in collection.
 * </p>
 *
 * @param <T>: RData type in collection.
 */
class RDList<T extends RData> extends SortableList<T> implements RData {
    private Review mHoldingReview;

    RDList() {
    }

    RDList(Review holdingReview) {
        mHoldingReview = holdingReview;
    }

    RDList(RDList<T> data, Review holdingReview) {
        add(data);
        mHoldingReview = holdingReview;
    }

    @Override
    public Review getHoldingReview() {
        return mHoldingReview;
    }

    @Override
    public void setHoldingReview(Review holdingReview) {
        mHoldingReview = holdingReview;
    }

    @Override
    public boolean hasData() {
        return mData.size() > 0;
    }
}
