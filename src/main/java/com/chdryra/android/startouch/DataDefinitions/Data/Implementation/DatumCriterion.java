/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.DataDefinitions.Data.Implementation;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataCriterion;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DatumCriterion implements DataCriterion {
    private final ReviewId mReviewId;
    private String mSubject;
    private float mRating;

    public DatumCriterion(ReviewId reviewId) {
        mReviewId = reviewId;
    }

    public DatumCriterion(ReviewId reviewId, String subject, float rating) {
        mReviewId = reviewId;
        mSubject = subject;
        mRating = rating;
    }

    @Override
    public String getSubject() {
        return mSubject;
    }

    @Override
    public float getRating() {
        return mRating;
    }

    @Override
    public ReviewId getReviewId() {
        return mReviewId;
    }

    @Override
    public boolean hasData(DataValidator validator) {
        return validator.validate(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DatumCriterion)) return false;

        DatumCriterion that = (DatumCriterion) o;

        if (Float.compare(that.mRating, mRating) != 0) return false;
        if (mReviewId != null ? !mReviewId.equals(that.mReviewId) : that.mReviewId != null)
            return false;
        return !(mSubject != null ? !mSubject.equals(that.mSubject) : that.mSubject != null);

    }

    @Override
    public int hashCode() {
        int result = mReviewId != null ? mReviewId.hashCode() : 0;
        result = 31 * result + (mSubject != null ? mSubject.hashCode() : 0);
        result = 31 * result + (mRating != +0.0f ? Float.floatToIntBits(mRating) : 0);
        return result;
    }

    @Override
    public String toString() {
        return StringParser.parse(this);
    }
}
