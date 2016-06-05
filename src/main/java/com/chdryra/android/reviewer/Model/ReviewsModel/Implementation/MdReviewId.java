/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;

/**
 * Review Data: Wrapper for a UUID
 * <p>
 * Use static methods <code>generateId(.)</code> to return a unique RDId.
 * </p>
 * <p/>
 * <p/>
 */
public class MdReviewId implements ReviewId, HasReviewId {
    private String mId;

    public MdReviewId(ReviewId id) {
        mId = id.toString();
    }

    public MdReviewId(String id) {
        mId = id;
    }

    @Override
    public ReviewId getReviewId() {
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReviewId)) return false;

        ReviewId that = (ReviewId) o;

        return mId.equals(that.toString());
    }

    @Override
    public int hashCode() {
        return mId.hashCode();
    }

    public String toString() {
        return mId;
    }
}