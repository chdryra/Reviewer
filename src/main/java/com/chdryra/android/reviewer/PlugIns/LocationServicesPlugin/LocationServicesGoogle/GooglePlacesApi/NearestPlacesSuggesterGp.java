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
import com.chdryra.android.reviewer.LocationServices.Interfaces.NearestPlacesSuggester;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 11/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class NearestPlacesSuggesterGp implements NearestPlacesSuggester, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, ResultCallback<PlaceLikelihoodBuffer> {
    private final GoogleApiClient mClient;
    private NearestPlacesListener mListener;


    public NearestPlacesSuggesterGp(GoogleApiClient client) {
        mClient = client;
        mClient.registerConnectionCallbacks(this);
        mClient.registerConnectionFailedListener(this);
    }

    @Override
    public void fetchSuggestions(LocatedPlace place, NearestPlacesListener listener) {
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
        mListener.onNearestPlacesFound(new ArrayList<LocatedPlace>());
    }

    private void doFetch() {
        boolean permission = ContextCompat.checkSelfPermission(mClient.getContext(), android
                .Manifest.permission
                .ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission( mClient.getContext(),
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        if(!permission) {
            mListener.onNotPermissioned();
            return;
        }

        Places.PlaceDetectionApi.getCurrentPlace(mClient, null).setResultCallback(this);
    }

    @Override
    public void onResult(@NonNull PlaceLikelihoodBuffer placeLikelihoods) {
        ArrayList<LocatedPlace> suggestions = new ArrayList<>();
        for(PlaceLikelihood likelyPlace : placeLikelihoods) {
            Place place = likelyPlace.getPlace();
            suggestions.add(new GooglePlace(place.getId(), place.getName().toString(), place.getLatLng()));
        }

        mListener.onNearestPlacesFound(suggestions);
        placeLikelihoods.release();
        mClient.disconnect();
    }
}
