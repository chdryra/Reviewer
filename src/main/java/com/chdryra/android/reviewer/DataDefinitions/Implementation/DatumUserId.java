/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.DataDefinitions.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.UserId;

/**
 * Created by: Rizwan Choudrey
 * On: 12/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DatumUserId implements UserId {
    private String mId;

    public DatumUserId(String id) {
        mId = id;
    }

    @Override
    public String toString() {
        return mId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserId)) return false;

        UserId that = (UserId) o;

        return !(mId != null ? !mId.equals(that.toString()) : that.toString() != null);

    }

    @Override
    public int hashCode() {
        return mId != null ? mId.hashCode() : 0;
    }
}
