/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.Model;

import com.chdryra.android.mygenerallibrary.SortableList;

/**
 * Review Data: Sortable collection of {@link MdData} objects that itself is considered Review Data
 * <p>
 * {@link #hasData()}: at least 1 object in collection.
 * </p>
 *
 * @param <T>: {@link MdData} type in collection.
 */
public class MdDataList<T extends MdData> extends SortableList<T> implements MdData {
    private final ReviewId mReviewId;

    public MdDataList(ReviewId reviewId) {
        mReviewId = reviewId;
    }

    @Override
    public ReviewId getReviewId() {
        return mReviewId;
    }

    @Override
    public boolean hasData() {
        return mData.size() > 0;
    }
}
