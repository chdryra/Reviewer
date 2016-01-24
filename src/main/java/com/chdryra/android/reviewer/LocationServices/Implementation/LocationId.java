/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.LocationServices.Implementation;

/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class LocationId {
    private LocationProvider mProvider;
    private String mId;

    public LocationId(LocationProvider provider, String providerId) {
        mProvider = provider;
        mId = providerId;
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
