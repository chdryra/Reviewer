/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

/**
 * Review Data: comment
 * <p>
 * {@link #hasData()}: A string at least 1 character in length.
 * </p>
 */
public class RDComment implements RData {
    private final String mComment;
    private       Review mHoldingReview;

    public RDComment(String comment, Review holdingReview) {
        mComment = comment;
        mHoldingReview = holdingReview;
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
        return mComment != null && mComment.length() > 0;
    }

    String get() {
        return mComment;
    }
}
