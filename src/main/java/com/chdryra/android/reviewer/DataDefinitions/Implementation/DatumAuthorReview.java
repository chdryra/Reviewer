/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.DataDefinitions.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.AuthorId;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DatumAuthorReview implements DataAuthorReview {
    private ReviewId mReviewId;
    private String mName;
    private AuthorId mAuthorId;

    public DatumAuthorReview(ReviewId reviewId, String name, AuthorId authorId) {
        mReviewId = reviewId;
        mName = name;
        mAuthorId = authorId;
    }

    @Override
    public String getName() {
        return mName;
    }

    public AuthorId getAuthorId() {
        return mAuthorId;
    }

    @Override
    public boolean hasData(DataValidator dataValidator) {
        return dataValidator.validate(this);
    }

    @Override
    public ReviewId getReviewId() {
        return mReviewId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DatumAuthorReview)) return false;

        DatumAuthorReview that = (DatumAuthorReview) o;

        if (!mReviewId.equals(that.mReviewId)) return false;
        if (!mName.equals(that.mName)) return false;
        return mAuthorId.equals(that.mAuthorId);

    }

    @Override
    public int hashCode() {
        int result = mReviewId.hashCode();
        result = 31 * result + mName.hashCode();
        result = 31 * result + mAuthorId.hashCode();
        return result;
    }
}
