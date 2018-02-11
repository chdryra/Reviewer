/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.DataDefinitions.Data.Implementation;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorName;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataAuthorName;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DatumAuthorName implements DataAuthorName {
    private ReviewId mReviewId;
    private String mName;
    private AuthorId mAuthorId;

    public DatumAuthorName() {
    }

    public DatumAuthorName(ReviewId reviewId, String name, AuthorId authorId) {
        mReviewId = reviewId;
        mName = name;
        mAuthorId = authorId;
    }

    public DatumAuthorName(ReviewId reviewId, AuthorName author) {
        mReviewId = reviewId;
        mName = author.getName();
        mAuthorId = author.getAuthorId();
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
        return dataValidator.validate((AuthorName)this);
    }

    @Override
    public ReviewId getReviewId() {
        return mReviewId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DatumAuthorName)) return false;

        DatumAuthorName that = (DatumAuthorName) o;

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

    @Override
    public String toString() {
        return StringParser.parse((AuthorName)this);
    }
}
