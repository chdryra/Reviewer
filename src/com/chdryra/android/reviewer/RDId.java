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
 * <code>hasData()</code>: true
 * </p>
 * <p/>
 * //TODO There's a reason couldn't use holding review but can't remember. Find out.
 */
class RDId implements RData {
    private final UUID mId;

    private RDId() {
        mId = UUID.randomUUID();
    }

    private RDId(String rdId) {
        mId = UUID.fromString(rdId);
    }

    static RDId generateId() {
        return new RDId();
    }

    static RDId generateId(String rdId) {
        return new RDId(rdId);
    }

    /**
     * To facilitate RCollectionReview
     */
    public interface RDIdAble {
        public RDId getId();
    }

    @Override
    public Review getHoldingReview() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setHoldingReview(Review review) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasData() {
        return true;
    }

    public boolean equals(RDId rdId) {
        return mId.equals(rdId.mId);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }

        RDId objId = (RDId) obj;
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