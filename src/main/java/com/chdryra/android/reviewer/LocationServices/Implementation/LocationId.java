/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.LocationServices.Implementation;

import android.support.annotation.Nullable;

/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class LocationId {
    private static final String NULL_ID = "NULL_LOCATION_ID";
    private LocationProvider mProvider;
    private String mId;

    public LocationId(LocationProvider provider, @Nullable String providerId) {
        mProvider = provider;
        mId = providerId != null ? providerId : NULL_ID;
    }

    public String getId() {
        return mId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LocationId)) return false;

        LocationId that = (LocationId) o;

        if (!mId.equals(that.mId)) return false;
        if (mProvider != that.mProvider) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = mProvider.hashCode();
        result = 31 * result + mId.hashCode();
        return result;
    }
}
