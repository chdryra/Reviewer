/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.LocationServicesGoogle.GooglePlacesApi;


import com.chdryra.android.reviewer.LocationServices.Implementation.LocationId;
import com.chdryra.android.reviewer.LocationServices.Interfaces.LocatedPlace;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 12/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GooglePlace implements LocatedPlace {
    private static final LatLng NULL_LAT_LNG = new LatLng(0,0);
    private static final String NULL_ID = "NULL_ID";
    private static final String NULL_DESC = "NULL_PLACE";

    private Place mPlace;

    public GooglePlace() {
    }

    public GooglePlace(Place place) {
        mPlace = place.freeze();
    }

    @Override
    public LatLng getLatLng() {
        return mPlace != null ? mPlace.getLatLng() : NULL_LAT_LNG;
    }

    @Override
    public String getDescription() {
        return mPlace != null ?
                mPlace.getName().toString() + ", " + mPlace.getAddress().toString() : NULL_DESC;
    }

    @Override
    public LocationId getId() {
        return new LocationId(GoogleLocationProvider.GOOGLE, mPlace != null ? mPlace.getId() : NULL_ID);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GooglePlace)) return false;

        GooglePlace that = (GooglePlace) o;

        return !(mPlace != null ? !mPlace.equals(that.mPlace) : that.mPlace != null);

    }

    @Override
    public int hashCode() {
        return mPlace != null ? mPlace.hashCode() : 0;
    }
}
