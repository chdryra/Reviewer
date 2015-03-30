/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.Model;

/**
 * Review Data: rating
 * <p>
 * {@link #hasData()}: true
 * </p>
 */
public class MdRating implements MdData {
    private final float  mRating;
    private final Review mHoldingReview;

    public MdRating(float rating, Review holdingReview) {
        mRating = rating;
        mHoldingReview = holdingReview;
    }

    @Override
    public Review getHoldingReview() {
        return mHoldingReview;
    }

    @Override
    public boolean hasData() {
        return true;
    }

    public float get() {
        return mRating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MdRating)) return false;

        MdRating mdRating = (MdRating) o;

        if (Float.compare(mdRating.mRating, mRating) != 0) return false;
        if (mHoldingReview != null ? !mHoldingReview.equals(mdRating.mHoldingReview) : mdRating
                .mHoldingReview != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = (mRating != +0.0f ? Float.floatToIntBits(mRating) : 0);
        result = 31 * result + (mHoldingReview != null ? mHoldingReview.hashCode() : 0);
        return result;
    }
}
