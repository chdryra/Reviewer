/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.Model;

/**
 * Defines interface for Review Data, data that reviews hold and can return.
 * <p/>
 * <p>
 * Review Data hold a reference to the review they belong to and can confirm that they have
 * data.
 * </p>
 */

//TODO need to make sure holding review is consistent for all the node wrapping etc. going on.
// Possibly better to calculate this on the fly using a node visitor as and when needed.
public interface MdData {
    Review getHoldingReview();

    boolean hasData();

    @Override
    public boolean equals(Object obj);

    @Override
    public int hashCode();
}
