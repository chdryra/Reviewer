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
    private final ReviewId mReviewId;

    public MdRating(float rating, ReviewId reviewId) {
        mRating = rating;
        mReviewId = reviewId;
    }

    @Override
    public ReviewId getReviewId() {
        return mReviewId;
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
        if (mReviewId != null ? !mReviewId.equals(mdRating.mReviewId) : mdRating
                .mReviewId != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = (mRating != +0.0f ? Float.floatToIntBits(mRating) : 0);
        result = 31 * result + (mReviewId != null ? mReviewId.hashCode() : 0);
        return result;
    }
}
