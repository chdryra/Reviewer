/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.DataDefinitions.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DatumRating implements DataRating {
    private float mRating;
    private int mRatingWeight;
    private ReviewId mReviewId;

    public DatumRating(ReviewId reviewId, float rating, int ratingWeight) {
        mRating = rating;
        mRatingWeight = ratingWeight;
        mReviewId = reviewId;
    }

    @Override
    public float getRating() {
        return mRating;
    }

    @Override
    public int getRatingWeight() {
        return mRatingWeight;
    }

    @Override
    public ReviewId getReviewId() {
        return mReviewId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DatumRating)) return false;

        DatumRating that = (DatumRating) o;

        if (Float.compare(that.mRating, mRating) != 0) return false;
        if (mRatingWeight != that.mRatingWeight) return false;
        return mReviewId.equals(that.mReviewId);

    }

    @Override
    public int hashCode() {
        int result = (mRating != +0.0f ? Float.floatToIntBits(mRating) : 0);
        result = 31 * result + mRatingWeight;
        result = 31 * result + mReviewId.hashCode();
        return result;
    }

    @Override
    public boolean hasData(DataValidator validator) {
        return validator.validate(this);
    }
}
