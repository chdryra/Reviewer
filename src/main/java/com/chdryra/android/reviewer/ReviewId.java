/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import java.util.UUID;

/**
 * Review Data: Wrapper for a UUID
 * <p>
 * Use static methods <code>generateId(.)</code> to return a unique RDId.
 * </p>
 * <p/>
 * <p>
 * {@link #hasData()}: true
 * </p>
 * <p/>
 * //TODO There's a reason couldn't use holding review but can't remember. Find out.
 */
public class ReviewId implements MdData {
    private final UUID mId;

    /**
     * To facilitate RCollectionReview
     */
    public interface RDIdAble {
        public ReviewId getId();
    }

    private ReviewId() {
        mId = UUID.randomUUID();
    }

    private ReviewId(String rdId) {
        mId = UUID.fromString(rdId);
    }

    public static ReviewId generateId() {
        return new ReviewId();
    }

    static ReviewId generateId(String rdId) {
        return new ReviewId(rdId);
    }

    @Override
    public Review getHoldingReview() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasData() {
        return true;
    }

    public boolean equals(ReviewId reviewId) {
        return mId.equals(reviewId.mId);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }

        ReviewId objId = (ReviewId) obj;
        return this.mId.equals(objId.mId);
    }

    @Override
    public int hashCode() {
        return mId.hashCode();
    }

    public String toString() {
        return mId.toString();
    }
}