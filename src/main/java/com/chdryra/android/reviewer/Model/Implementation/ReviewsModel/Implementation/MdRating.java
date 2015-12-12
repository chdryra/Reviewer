/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;

/**
 * Review Data: rating
 */
public class MdRating implements DataRating {
    private final float mRating;
    private final int mWeight;
    private final MdReviewId mReviewId;

    //Constructors
    public MdRating(MdReviewId reviewId, float rating, int weight) {
        mRating = rating;
        mReviewId = reviewId;
        mWeight = weight;
    }

    //Overridden
    @Override
    public float getRating() {
        return mRating;
    }

    @Override
    public int getRatingWeight() {
        return mWeight;
    }

    @Override
    public ReviewId getReviewId() {
        return mReviewId;
    }

    @Override
    public boolean hasData(DataValidator dataValidator) {
        return true;
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
