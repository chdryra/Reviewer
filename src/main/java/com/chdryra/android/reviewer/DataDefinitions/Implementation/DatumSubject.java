package com.chdryra.android.reviewer.DataDefinitions.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DatumSubject implements DataSubject {
    private final ReviewId mReviewId;
    private final String mSubject;

    public DatumSubject(ReviewId reviewId, String subject) {
        mReviewId = reviewId;
        mSubject = subject;
    }

    @Override
    public String getSubject() {
        return mSubject;
    }

    @Override
    public ReviewId getReviewId() {
        return mReviewId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DatumSubject)) return false;

        DatumSubject that = (DatumSubject) o;

        if (!mReviewId.equals(that.mReviewId)) return false;
        return mSubject.equals(that.mSubject);

    }

    @Override
    public int hashCode() {
        int result = mReviewId.hashCode();
        result = 31 * result + mSubject.hashCode();
        return result;
    }

    @Override
    public boolean hasData(DataValidator validator) {


        return validator.validate(this);
    }
}
