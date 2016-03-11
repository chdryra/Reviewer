/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.LocationServicesGoogle.GooglePlacesApi;



import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.chdryra.android.reviewer.LocationServices.Interfaces.LocatedPlace;
import com.chdryra.android.reviewer.LocationServices.Interfaces.PlaceSearcher;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 11/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PlaceSearcherGp implements PlaceSearcher, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, ResultCallback<AutocompletePredictionBuffer> {
    private final GoogleApiClient mClient;
    private PlaceSearcherListener mListener;
    private LatLng mLatLng;
    private String mQuery;

    public PlaceSearcherGp(GoogleApiClient client) {
        mClient = client;
        mClient.registerConnectionCallbacks(this);
        mClient.registerConnectionFailedListener(this);
    }

    @Override
    public void searchQuery(String query, LatLng nearLatLng, PlaceSearcherListener listener) {
        mQuery = query;
        mLatLng = nearLatLng;
        mListener = listener;

        if(!mClient.isConnected()) {
            mClient.connect();
        } else {
            doFetch();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        doFetch();
    }

    @Override
    public void onConnectionSuspended(int i) {
        cannotFetch();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        cannotFetch();
    }

    private void cannotFetch() {
        mListener.onSearchResultsFound(new ArrayList<LocatedPlace>());
    }

    private void doFetch() {
        if(!hasPermission()) {
            mListener.onNotPermissioned();
            return;
        }

        LatLngBounds bounds = LatLngBounds.builder().include(mLatLng).build();
        AutocompleteFilter filter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_NONE)
                .build();

        Places.GeoDataApi
                .getAutocompletePredictions(mClient, mQuery, bounds, filter)
                .setResultCallback(this);}

    private boolean hasPermission() {
        return ContextCompat.checkSelfPermission(mClient.getContext(), android
                .Manifest.permission
                .ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission( mClient.getContext(),
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onResult(@NonNull AutocompletePredictionBuffer predictions) {
        ArrayList<LocatedPlace> results = new ArrayList<>();
        for(AutocompletePrediction prediction : predictions) {
            results.add(new GoogleAutoCompletePlace(prediction.getPlaceId(),
                    prediction.getPrimaryText(null).toString(), mLatLng));
        }

        mListener.onSearchResultsFound(results);
        predictions.release();
    }
}
