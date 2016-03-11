/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.LocationServicesGoogle.GooglePlacesApi;


import android.net.Uri;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by: Rizwan Choudrey
 * On: 11/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
class NullPlace implements Place {
    private static final LatLng NULL_LAT_LNG = new LatLng(0, 0);
    private static final String NULL_ID = "NULL_ID";
    private static final String NULL_DESC = "NULL_PLACE";

    @Override
    public String getId() {
        return NULL_ID;
    }

    @Override
    public List<Integer> getPlaceTypes() {
        return new ArrayList<>();
    }

    @Override
    public CharSequence getAddress() {
        return NULL_DESC;
    }

    @Override
    public Locale getLocale() {
        return Locale.getDefault();
    }

    @Override
    public CharSequence getName() {
        return NULL_DESC;
    }

    @Override
    public LatLng getLatLng() {
        return NULL_LAT_LNG;
    }

    @Override
    public LatLngBounds getViewport() {
        return null;
    }

    @Override
    public Uri getWebsiteUri() {
        return new Uri.Builder().build();
    }

    @Override
    public CharSequence getPhoneNumber() {
        return NULL_DESC;
    }

    @Override
    public float getRating() {
        return 0;
    }

    @Override
    public int getPriceLevel() {
        return 0;
    }

    @Override
    public CharSequence getAttributions() {
        return NULL_DESC;
    }

    @Override
    public Place freeze() {
        return this;
    }

    @Override
    public boolean isDataValid() {
        return false;
    }
}
