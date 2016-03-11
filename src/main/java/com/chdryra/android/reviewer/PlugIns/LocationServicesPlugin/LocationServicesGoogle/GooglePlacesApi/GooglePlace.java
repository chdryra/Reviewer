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
    private Place mPlace;

    public GooglePlace(Place place) {
        mPlace = place.freeze();
    }

    @Override
    public LatLng getLatLng() {
        return mPlace.getLatLng();
    }

    @Override
    public String getDescription() {
        return mPlace.getName().toString() + ", " + mPlace.getAddress().toString();
    }

    @Override
    public LocationId getId() {
        return new LocationId(GoogleLocationProvider.GOOGLE, mPlace.getId());
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
