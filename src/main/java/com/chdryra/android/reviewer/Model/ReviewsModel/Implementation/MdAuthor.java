/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.AuthorId;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdAuthor implements DataAuthorReview {
    private ReviewId mReviewId;
    private String mName;
    private AuthorId mAuthorId;

    public MdAuthor(ReviewId reviewId, String name, AuthorId authorId) {
        mReviewId = reviewId;
        mName = name;
        mAuthorId = authorId;
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public AuthorId getAuthorId() {
        return mAuthorId;
    }

    @Override
    public boolean hasData(DataValidator dataValidator) {
        return dataValidator.validate(mReviewId) &&
                dataValidator.validateString(mName) && dataValidator.validate(mAuthorId);
    }

    @Override
    public ReviewId getReviewId() {
        return mReviewId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MdAuthor)) return false;

        MdAuthor mdAuthor = (MdAuthor) o;

        if (mReviewId != null ? !mReviewId.equals(mdAuthor.mReviewId) : mdAuthor.mReviewId != null)
            return false;
        if (mName != null ? !mName.equals(mdAuthor.mName) : mdAuthor.mName != null) return false;
        return !(mAuthorId != null ? !mAuthorId.equals(mdAuthor.mAuthorId) : mdAuthor.mAuthorId != null);

    }

    @Override
    public int hashCode() {
        int result = mReviewId != null ? mReviewId.hashCode() : 0;
        result = 31 * result + (mName != null ? mName.hashCode() : 0);
        result = 31 * result + (mAuthorId != null ? mAuthorId.hashCode() : 0);
        return result;
    }
}
