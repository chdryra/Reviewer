/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdAuthorId implements DataAuthorId {
    private ReviewId mReviewId;
    private AuthorId mAuthorId;

    public MdAuthorId(ReviewId reviewId, AuthorId authorId) {
        mReviewId = reviewId;
        mAuthorId = authorId;
    }

    @Override
    public boolean hasData(DataValidator dataValidator) {
        return dataValidator.validate(mReviewId) && dataValidator.validate(mAuthorId);
    }

    @Override
    public ReviewId getReviewId() {
        return mReviewId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MdAuthorId)) return false;

        MdAuthorId that = (MdAuthorId) o;

        if (!mReviewId.equals(that.mReviewId)) return false;
        return mAuthorId.equals(that.mAuthorId);

    }

    @Override
    public int hashCode() {
        int result = mReviewId.hashCode();
        result = 31 * result + mAuthorId.hashCode();
        return result;
    }
}
