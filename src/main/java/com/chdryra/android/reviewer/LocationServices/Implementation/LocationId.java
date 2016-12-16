/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.LocationServices.Implementation;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.ReviewStamp;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class LocationId {
    private static final String NULL_ID = "NULL_LOCATION_ID";
    private static final String SEPARATOR = ":";
    private final LocationProvider mProvider;
    private final String mId;

    public LocationId(LocationProvider provider, @Nullable String providerId) {
        mProvider = provider;
        mId = providerId != null ? providerId : NULL_ID;
    }

    public LocationProvider getProvider() {
        return mProvider;
    }

    public static LocationId appLocationId(String name, LatLng latLng) {
        return new LocationId(LocationProvider.app(), name + SEPARATOR + latLng);
    }

    public static LocationId nullId() {
        return new LocationId(LocationProvider.app(), ReviewStamp.noStamp().toString()
                + SEPARATOR + "NULL");
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
        return mProvider == that.mProvider;
    }

    @Override
    public int hashCode() {
        int result = mProvider.hashCode();
        result = 31 * result + mId.hashCode();
        return result;
    }
}
