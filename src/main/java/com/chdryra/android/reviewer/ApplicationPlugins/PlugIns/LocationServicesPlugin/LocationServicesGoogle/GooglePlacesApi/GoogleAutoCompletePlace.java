/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.LocationServicesPlugin.LocationServicesGoogle.GooglePlacesApi;

import com.chdryra.android.reviewer.LocationServices.Implementation.LocationId;
import com.chdryra.android.reviewer.LocationServices.Interfaces.LocatedPlace;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 12/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GoogleAutoCompletePlace implements LocatedPlace {
    private AutocompletePrediction mPrediction;
    private LatLng mLatLng;

    public GoogleAutoCompletePlace(AutocompletePrediction prediction, LatLng latLng) {
        mPrediction = prediction.freeze();
        mLatLng = latLng;
    }

    @Override
    public LatLng getLatLng() {
        return mLatLng;
    }

    @Override
    public String getDescription() {
        return mPrediction.getPrimaryText(null).toString() + ", " + mPrediction.getSecondaryText(null).toString();
    }

    @Override
    public LocationId getId() {
        return new LocationId(GoogleLocationProvider.GOOGLE, mPrediction.getPlaceId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GoogleAutoCompletePlace)) return false;

        GoogleAutoCompletePlace that = (GoogleAutoCompletePlace) o;

        if (mPrediction != null ? !mPrediction.equals(that.mPrediction) : that.mPrediction != null)
            return false;
        return !(mLatLng != null ? !mLatLng.equals(that.mLatLng) : that.mLatLng != null);

    }

    @Override
    public int hashCode() {
        int result = mPrediction != null ? mPrediction.hashCode() : 0;
        result = 31 * result + (mLatLng != null ? mLatLng.hashCode() : 0);
        return result;
    }
}
