/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

public class RDSubject implements RData {
    private final String mTitle;
    private       Review mHoldingReview;

    public RDSubject(String title, Review review) {
        mTitle = title;
        mHoldingReview = review;
    }

    public String get() {
        return mTitle;
    }

    @Override
    public Review getHoldingReview() {
        return mHoldingReview;
    }

    @Override
    public void setHoldingReview(Review review) {
        mHoldingReview = review;
    }

    @Override
    public boolean hasData() {
        return mTitle != null && mTitle.length() > 0;
    }
}
