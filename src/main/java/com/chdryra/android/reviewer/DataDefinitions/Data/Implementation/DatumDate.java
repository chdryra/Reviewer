/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.DataDefinitions.Data.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DatumDate implements DataDate {
    private ReviewId mReviewId;
    private long mTime;

    public DatumDate() {
    }

    public DatumDate(ReviewId reviewId, long time) {
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
        if (!(o instanceof DatumDate)) return false;

        DatumDate that = (DatumDate) o;

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
