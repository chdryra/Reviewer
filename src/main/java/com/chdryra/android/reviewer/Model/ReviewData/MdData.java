/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.Model.ReviewData;

/**
 * Defines interface for Review Data, data that reviews hold and can return.
 * <p/>
 * <p>
 * Review Data hold a reference to the review they belong to and can confirm that they have
 * data.
 * </p>
 */

public interface MdData {
    //abstract
    ReviewId getReviewId();

    boolean hasData();

    //Overridden
    @Override
    boolean equals(Object obj);

    @Override
    int hashCode();
}
