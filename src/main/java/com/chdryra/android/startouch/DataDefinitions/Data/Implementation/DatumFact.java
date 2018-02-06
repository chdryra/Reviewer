/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.DataDefinitions.Data.Implementation;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataFact;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DatumFact implements DataFact {
    private final ReviewId mReviewId;
    private String mLabel;
    private String mValue;
    private boolean mIsUrl;

    public DatumFact(ReviewId reviewId) {
        mReviewId = reviewId;
    }

    public DatumFact(ReviewId reviewId, String label, String value) {
        this(reviewId, label, value, false);
    }

    public DatumFact(ReviewId reviewId, String label, String value, boolean isUrl) {
        mReviewId = reviewId;
        mLabel = label;
        mValue = value;
        mIsUrl = isUrl;
    }

    @Override
    public String getLabel() {
        return mLabel;
    }

    @Override
    public String getValue() {
        return mValue;
    }

    @Override
    public boolean isUrl() {
        return mIsUrl;
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
        if (!(o instanceof DatumFact)) return false;

        DatumFact datumFact = (DatumFact) o;

        if (mReviewId != null ? !mReviewId.equals(datumFact.mReviewId) : datumFact.mReviewId !=
                null)
            return false;
        if (mLabel != null ? !mLabel.equals(datumFact.mLabel) : datumFact.mLabel != null)
            return false;
        return !(mValue != null ? !mValue.equals(datumFact.mValue) : datumFact.mValue != null);

    }

    @Override
    public int hashCode() {
        int result = mReviewId != null ? mReviewId.hashCode() : 0;
        result = 31 * result + (mLabel != null ? mLabel.hashCode() : 0);
        result = 31 * result + (mValue != null ? mValue.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return StringParser.parse(this);
    }
}
