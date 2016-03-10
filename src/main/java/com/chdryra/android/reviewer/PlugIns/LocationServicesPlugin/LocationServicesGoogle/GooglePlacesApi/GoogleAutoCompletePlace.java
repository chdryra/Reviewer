/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.LocationServicesGoogle.GooglePlacesApi;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.LocationServices.Implementation.LocationId;
import com.chdryra.android.reviewer.LocationServices.Interfaces.LocatedPlace;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.LocationServicesGoogle.Implementation.GoogleLocationProvider;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 12/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GoogleAutoCompletePlace implements LocatedPlace {
    private String mId;
    private String mDescription;
    private LatLng mLatLng;

    public GoogleAutoCompletePlace(@Nullable String id, String description, LatLng latLng) {
        mId = id == null ? "" : id;
        mDescription = description;
        mLatLng = latLng;
    }

    @Override
    public LatLng getLatLng() {
        return mLatLng;
    }

    @Override
    public String getDescription() {
        return mDescription;
    }

    @Override
    public LocationId getId() {
        return new LocationId(GoogleLocationProvider.GOOGLE, mId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GoogleAutoCompletePlace)) return false;

        GoogleAutoCompletePlace that = (GoogleAutoCompletePlace) o;

        if (mId != null ? !mId.equals(that.mId) : that.mId != null) return false;
        if (mDescription != null ? !mDescription.equals(that.mDescription) : that.mDescription !=
                null)
            return false;
        return !(mLatLng != null ? !mLatLng.equals(that.mLatLng) : that.mLatLng != null);

    }

    @Override
    public int hashCode() {
        int result = mId != null ? mId.hashCode() : 0;
        result = 31 * result + (mDescription != null ? mDescription.hashCode() : 0);
        result = 31 * result + (mLatLng != null ? mLatLng.hashCode() : 0);
        return result;
    }
}
