/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.LocationServicesGoogle.GooglePlacesApi;


import com.chdryra.android.reviewer.LocationServices.Interfaces.LocationDetails;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 14/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class LocationDetailsGoogle implements LocationDetails {
    private static final LatLng NULL_LAT_LNG = new LatLng(0,0);
    private static final String NULL_DESC = "NULL_PLACE";

    private Place mPlace;

    public LocationDetailsGoogle() {
    }

    public LocationDetailsGoogle(Place place) {
        mPlace = place;
    }

    @Override
    public LatLng getLatLng() {
        return mPlace != null ? mPlace.getLatLng() : NULL_LAT_LNG;
    }

    @Override
    public String getDescription() {
        return mPlace != null ? mPlace.getName().toString() : NULL_DESC;
    }
}
