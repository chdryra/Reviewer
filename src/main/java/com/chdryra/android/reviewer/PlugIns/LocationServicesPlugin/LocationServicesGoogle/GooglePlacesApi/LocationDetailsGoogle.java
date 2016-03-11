/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.LocationServicesGoogle.GooglePlacesApi;


import android.net.Uri;

import com.chdryra.android.reviewer.LocationServices.Implementation.LocationProvider;
import com.chdryra.android.reviewer.LocationServices.Interfaces.LocationDetails;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;

import java.util.Locale;

/**
 * Created by: Rizwan Choudrey
 * On: 14/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class LocationDetailsGoogle implements LocationDetails{
    private static final Place NULL_PLACE = new NullPlace();
    private static final LocationProvider GOOGLE = GoogleLocationProvider.GOOGLE;

    private Place mPlace;

    public LocationDetailsGoogle() {
        mPlace = NULL_PLACE;
    }

    public LocationDetailsGoogle(Place place) {
        mPlace = place.freeze();
    }

    @Override
    public LatLng getLatLng() {
        return mPlace.getLatLng();
    }

    @Override
    public String getId() {
        return mPlace.getId();
    }

    @Override
    public LocationProvider getProvider() {
        return GOOGLE;
    }

    @Override
    public String getName() {
        return mPlace.getName().toString();
    }

    @Override
    public String getAddress() {
        return mPlace.getAddress().toString();
    }

    @Override
    public String getAttributions() {
        return mPlace.getAttributions().toString();
    }

    @Override
    public Locale getLocale() {
        return mPlace.getLocale();
    }

    @Override
    public String getPhoneNumber() {
        return mPlace.getPhoneNumber().toString();
    }

    @Override
    public Uri getWebsiteUri() {
        return mPlace.getWebsiteUri();
    }
}
