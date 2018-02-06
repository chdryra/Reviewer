/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.DataDefinitions.Data.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataTag;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DatumTag implements DataTag {
    private ReviewId mReviewId;
    private String mTag;

    public DatumTag() {
    }

    public DatumTag(ReviewId reviewId) {
        mReviewId = reviewId;
    }

    public DatumTag(ReviewId reviewId, @NonNull String tag) {
        mReviewId = reviewId;
        mTag = tag;
    }

    @Override
    public String getTag() {
        return mTag;
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
        if (!(o instanceof DatumTag)) return false;

        DatumTag datumTag = (DatumTag) o;

        if (!mReviewId.equals(datumTag.mReviewId)) return false;
        return mTag.equals(datumTag.mTag);

    }

    @Override
    public int hashCode() {
        int result = mReviewId.hashCode();
        result = 31 * result + mTag.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return StringParser.parse(this);
    }
}
