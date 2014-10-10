/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

/**
 * Review Data: fact
 * <p>
 * <code>hasData()</code>: Label and value strings at least 1 character in length
 * </p>
 */
class RDFact implements RData {

    private final String mLabel;
    private final String mValue;
    private       Review mHoldingReview;

    RDFact(String label, String value, Review holdingReview) {
        mLabel = label;
        mValue = value;
        mHoldingReview = holdingReview;
    }

    String getLabel() {
        return mLabel;
    }

    String getValue() {
        return mValue;
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
        return mLabel != null && mValue != null && mLabel.length() > 0 && mValue.length() > 0;
    }
}
