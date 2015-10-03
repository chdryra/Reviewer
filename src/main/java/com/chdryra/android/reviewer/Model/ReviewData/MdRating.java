/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.Model.ReviewData;

/**
 * Review Data: rating
 * <p>
 * {@link #hasData()}: true
 * </p>
 */
public class MdRating implements MdData {
    private final float  mRating;
    private final int  mWeight;
    private final ReviewId mReviewId;

    public MdRating(float rating, int weight, ReviewId reviewId) {
        mRating = rating;
        mReviewId = reviewId;
        mWeight = weight;
    }

    @Override
    public ReviewId getReviewId() {
        return mReviewId;
    }

    @Override
    public boolean hasData() {
        return true;
    }

    public float getValue() {
        return mRating;
    }

    public int getWeight() {
        return mWeight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MdRating)) return false;

        MdRating mdRating = (MdRating) o;

        if (Float.compare(mdRating.mRating, mRating) != 0) return false;
        if (mWeight != mdRating.mWeight) return false;
        return mReviewId.equals(mdRating.mReviewId);

    }

    @Override
    public int hashCode() {
        int result = (mRating != +0.0f ? Float.floatToIntBits(mRating) : 0);
        result = 31 * result + mWeight;
        result = 31 * result + mReviewId.hashCode();
        return result;
    }
}
