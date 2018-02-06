/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.DataDefinitions.Data.Implementation;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataSubject;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DatumSubject implements DataSubject {
    private ReviewId mReviewId;
    private String mSubject;

    public DatumSubject() {
    }

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
        return mSubject.equalsIgnoreCase(that.mSubject);

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

    @Override
    public String toString() {
        return StringParser.parse(this);
    }
}
