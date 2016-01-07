package com.chdryra.android.reviewer.DataDefinitions.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DatumDateReview implements DataDateReview {
    private ReviewId mReviewId;
    private long mTime;

    public DatumDateReview(ReviewId reviewId, long time) {
        mReviewId = reviewId;
        mTime = time;
    }

    @Override
    public long getTime() {
        return mTime;
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
        if (!(o instanceof DatumDateReview)) return false;

        DatumDateReview that = (DatumDateReview) o;

        if (mTime != that.mTime) return false;
        return mReviewId.equals(that.mReviewId);

    }

    @Override
    public int hashCode() {
        int result = mReviewId.hashCode();
        result = 31 * result + (int) (mTime ^ (mTime >>> 32));
        return result;
    }
}
