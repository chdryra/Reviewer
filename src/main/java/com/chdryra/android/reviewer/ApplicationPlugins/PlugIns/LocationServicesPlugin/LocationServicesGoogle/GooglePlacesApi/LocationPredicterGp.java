/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.LocationServicesPlugin.LocationServicesGoogle.GooglePlacesApi;



import android.os.Bundle;
import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.LocationServices.Implementation.AutoCompleterLocation;
import com.chdryra.android.reviewer.LocationServices.Interfaces.LocatedPlace;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 14/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class LocationPredicterGp implements AutoCompleterLocation.LocationPredicter, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private CallBackSignaler mSignaler;
    private GoogleApiClient mClient;

    public LocationPredicterGp(GoogleApiClient client) {
        mClient = client;
        mClient.registerConnectionCallbacks(this);
        mClient.registerConnectionFailedListener(this);
        mSignaler = new CallBackSignaler(5);
    }

    @Override
    public ArrayList<LocatedPlace> fetchPredictions(String query, LatLng latLng) {
        ArrayList<LocatedPlace> results = new ArrayList<>();
        if(!mClient.isConnected()) {
            connect();
            if (mSignaler.timedOut()) return results;
        }

        LatLngBounds bounds = LatLngBounds.builder().include(latLng).build();
        AutocompleteFilter filter
                = new AutocompleteFilter.Builder().setTypeFilter(AutocompleteFilter
                .TYPE_FILTER_NONE).build();
        PendingResult<AutocompletePredictionBuffer> result =
                Places.GeoDataApi.getAutocompletePredictions(mClient, query, bounds, filter);
        AutocompletePredictionBuffer predictions = result.await();

        for(AutocompletePrediction prediction : predictions) {
            results.add(new GoogleAutoCompletePlace(prediction.freeze(), latLng));
        }

        predictions.release();

        return results;
    }

    private void connect() {
        mSignaler.reset();
        mClient.connect();
        mSignaler.waitForSignal();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mSignaler.signal();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mSignaler.doNotSignal();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        mSignaler.doNotSignal();
    }

    @Override
    public void disconnect() {
        mClient.disconnect();
    }
}
