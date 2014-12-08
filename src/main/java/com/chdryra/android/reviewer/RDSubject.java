/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

/**
 * Review Data: subject
 * <p>
 * {@link #hasData()}: string at least 1 character in length.
 * </p>
 */
public class RDSubject implements RData {
    private final String mTitle;
    private       Review mHoldingReview;

    public RDSubject(String title, Review review) {
        mTitle = title;
        mHoldingReview = review;
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

    public String get() {
        return mTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RDSubject)) return false;

        RDSubject rdSubject = (RDSubject) o;

        if (mHoldingReview != null ? !mHoldingReview.equals(rdSubject.mHoldingReview) : rdSubject
                .mHoldingReview != null) {
            return false;
        }
        if (mTitle != null ? !mTitle.equals(rdSubject.mTitle) : rdSubject.mTitle != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = mTitle != null ? mTitle.hashCode() : 0;
        result = 31 * result + (mHoldingReview != null ? mHoldingReview.hashCode() : 0);
        return result;
    }
}
