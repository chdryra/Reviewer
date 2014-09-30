/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import java.util.UUID;

public class UserId {
    private final UUID mId;

    private UserId() {
        mId = UUID.randomUUID();
    }

    public static UserId generateId() {
        return new UserId();
    }

    public boolean equals(UserId userId) {
        return mId.equals(userId.mId);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }

        UserId objId = (UserId) obj;
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