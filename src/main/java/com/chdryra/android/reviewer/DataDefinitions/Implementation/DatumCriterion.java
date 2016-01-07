package com.chdryra.android.reviewer.DataDefinitions.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DatumCriterion implements DataCriterion {
    private ReviewId mReviewId;
    private String mSubject;
    private float mRating;

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
}
