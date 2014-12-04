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
 * Unique user ID for author credentials. A Wrapper for a UUID.
 * <p/>
 * <p>
 * Use static method <code>generateId()</code> to return a unique UserId.
 * </p>
 *
 * @see com.chdryra.android.reviewer.Author
 */

class UserId {
    public final static UserId NULL_ID = new UserId(null);
    private final UUID mId;

    private UserId(UUID id) {
        mId = id;
    }

    static UserId generateId() {
        return new UserId(UUID.randomUUID());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }

        UserId objId = (UserId) obj;
        UUID id = objId.mId;
        if (id == null && mId == null) {
            return true;
        } else if (mId != null) {
            return this.mId.equals(id);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return mId.hashCode();
    }

    @Override
    public String toString() {
        return mId.toString();
    }

    boolean equals(UserId userId) {
        return mId.equals(userId.mId);
    }
}