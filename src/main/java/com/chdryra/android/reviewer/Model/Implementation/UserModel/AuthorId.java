/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.Model.Implementation.UserModel;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.UserId;

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

public class AuthorId implements UserId {
    public final static String NULL_ID_STRING = "NULL";
    private final UUID mId;

    private AuthorId() {
        mId = null;
    }

    private AuthorId(UUID id) {
        mId = id;
    }

    private AuthorId(String rdId) {
        if (rdId.equals(NULL_ID_STRING)) {
            mId = null;
        } else {
            mId = UUID.fromString(rdId);
        }
    }

    //Static methods
    public static AuthorId generateId() {
        return new AuthorId(UUID.randomUUID());
    }

    public static AuthorId fromString(String rdId) {
        return new AuthorId(rdId);
    }

    @Override
    public String getId() {
        return toString();
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj.getClass() == getClass() && this.mId.equals(((AuthorId) obj).mId);
    }

    @Override
    public int hashCode() {
        return mId.hashCode();
    }

    @Override
    public String toString() {
        return mId != null ? mId.toString() : NULL_ID_STRING;
    }
}