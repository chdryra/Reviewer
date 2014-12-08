/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

/**
 * An extension of {@link RCollection} for objects that have their own reference {@link RDId} such
 * as {@link Review}s.
 *
 * @param <T>: type that is {@link com.chdryra.android.reviewer.RDId.RDIdAble}
 */
public class RCollectionReview<T extends RDId.RDIdAble> extends RCollection<T> {
    public void add(T review) {
        put(review.getId(), review);
    }
}
