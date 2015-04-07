/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.Model;

import java.util.UUID;

/**
 * Unique user ID for author credentials. A Wrapper for a UUID.
 * <p/>
 * <p>
 * Use static method <code>generateId()</code> to return a unique UserId.
 * </p>
 *
 * @see Author
 */

public class UserId {
    public final static UserId NULL_ID = new UserId();
    public final static String NULL_ID_STRING = "NULL";
    private final UUID mId;

    private UserId() {
        mId = null;
    }

    private UserId(UUID id) {
        mId = id;
    }

    private UserId(String rdId) {
        if (rdId.equals(NULL_ID_STRING)) {
            mId = null;
        } else {
            mId = UUID.fromString(rdId);
        }
    }

    public static UserId generateId() {
        return new UserId(UUID.randomUUID());
    }

    public static UserId fromString(String rdId) {
        return new UserId(rdId);
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj.getClass() == getClass() && this.equals((UserId) obj);
    }

    @Override
    public int hashCode() {
        return mId.hashCode();
    }

    @Override
    public String toString() {
        return mId != null ? mId.toString() : NULL_ID_STRING;
    }

    public boolean equals(UserId userId) {
        return mId.equals(userId.mId);
    }
}