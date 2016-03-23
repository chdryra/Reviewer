/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdDate implements DataDateReview {
    private long mTime;
    private MdReviewId mReviewId;

    public MdDate(MdReviewId reviewId, long time) {
        mTime = time;
        mReviewId = reviewId;
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
    public boolean hasData(DataValidator dataValidator) {
        return dataValidator.validate(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MdDate)) return false;

        MdDate mdDate = (MdDate) o;

        if (mTime != mdDate.mTime) return false;
        return !(mReviewId != null ? !mReviewId.equals(mdDate.mReviewId) : mdDate.mReviewId !=
                null);

    }

    @Override
    public int hashCode() {
        int result = (int) (mTime ^ (mTime >>> 32));
        result = 31 * result + (mReviewId != null ? mReviewId.hashCode() : 0);
        return result;
    }
}
