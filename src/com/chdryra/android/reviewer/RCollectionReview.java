/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

/**
 * An extension of RCollection for objects that have their own reference RDId such as Reviews.
 *
 * @param <T>: type that is RDIsAble
 */
class RCollectionReview<T extends RDId.RDIdAble> extends RCollection<T> {
    public void add(T review) {
        put(review.getId(), review);
    }
}
