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
 * Review Data: Sortable collection of {@link MdData} objects that itself is considered Review Data
 * <p>
 * {@link #hasData()}: at least 1 object in collection.
 * </p>
 *
 * @param <T>: {@link MdData} type in collection.
 */
public class MdList<T extends MdData> extends SortableList<T> implements MdData {
    private Review mHoldingReview;

    MdList(Review holdingReview) {
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
