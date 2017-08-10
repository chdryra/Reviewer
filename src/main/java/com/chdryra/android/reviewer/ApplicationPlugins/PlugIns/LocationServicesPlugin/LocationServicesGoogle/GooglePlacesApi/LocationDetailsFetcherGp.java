/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.LocationServicesPlugin.LocationServicesGoogle.GooglePlacesApi;



import android.support.annotation.NonNull;

import com.chdryra.android.mygenerallibrary.LocationServices.LocatedPlace;
import com.chdryra.android.mygenerallibrary.LocationServices.LocationDetails;
import com.chdryra.android.mygenerallibrary.LocationServices.LocationDetailsFetcher;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;

/**
 * Created by: Rizwan Choudrey
 * On: 11/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class LocationDetailsFetcherGp extends GoogleLocationServiceBasic implements
        LocationDetailsFetcher, ResultCallback<PlaceBuffer> {
    private LocatedPlace mPlace;
    private LocationDetailsListener mListener;

    public LocationDetailsFetcherGp(GoogleApiClient client) {
        super(client);
    }

    @Override
    public void fetchPlaceDetails(LocatedPlace place, LocationDetailsListener listener) {
        mPlace = place;
        mListener = listener;
        connectAndDoRequest();
    }

    @Override
    protected void onNotConnected() {
        mListener.onPlaceDetailsFound(new LocationDetailsGoogle());
    }

    @Override
    protected void onNotPermissioned() {
        mListener.onNotPermissioned();
    }

    @Override
    protected void doRequestOnConnected() {
        Places.GeoDataApi.getPlaceById(getClient(), mPlace.getId().getId()).setResultCallback(this);
    }

    @Override
    public void onResult(@NonNull PlaceBuffer places) {
        LocationDetails details;
        if (places.getStatus().isSuccess() && places.getCount() > 0) {
            details = new LocationDetailsGoogle(places.get(0).freeze());
        } else{
            details = new LocationDetailsGoogle();
        }

        mListener.onPlaceDetailsFound(details);

        places.release();

        disconnect();
    }
}
