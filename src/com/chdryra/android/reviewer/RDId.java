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
 * Wrapper for a UUID
 */
class RDId implements RData {
    private final UUID mId;

    private RDId() {
        mId = UUID.randomUUID();
    }

    private RDId(String rdId) {
        mId = UUID.fromString(rdId);
    }

    public static RDId generateId() {
        return new RDId();
    }

    public static RDId generateId(String rdId) {
        return new RDId(rdId);
    }

    @Override
    public ReviewEditable getHoldingReview() {
        return FactoryReview.createNullReview();
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