/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.DataDefinitions.Data.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 12/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DatumAuthorId implements DataAuthorId {
    private ReviewId mReviewId;
    private String mAuthorId;

    public DatumAuthorId() {
    }

    public DatumAuthorId(ReviewId reviewId, String authorId) {
        mReviewId = reviewId;
        mAuthorId = authorId;
    }

    @Override
    public String toString() {
        return mAuthorId;
    }

    @Override
    public boolean hasData(DataValidator dataValidator) {
        return dataValidator.validate(mReviewId) && dataValidator.validate(this);
    }

    @Override
    public ReviewId getReviewId() {
        return mReviewId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DatumAuthorId)) return false;

        DatumAuthorId that = (DatumAuthorId) o;

        if (mReviewId != null ? !mReviewId.equals(that.mReviewId) : that.mReviewId != null)
            return false;
        return mAuthorId != null ? mAuthorId.equals(that.mAuthorId) : that.mAuthorId == null;

    }

    @Override
    public int hashCode() {
        int result = mReviewId != null ? mReviewId.hashCode() : 0;
        result = 31 * result + (mAuthorId != null ? mAuthorId.hashCode() : 0);
        return result;
    }
}
