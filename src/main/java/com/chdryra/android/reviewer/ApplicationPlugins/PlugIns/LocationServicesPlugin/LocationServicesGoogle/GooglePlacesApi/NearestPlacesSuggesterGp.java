/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.LocationServicesPlugin.LocationServicesGoogle
        .GooglePlacesApi;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.chdryra.android.mygenerallibrary.LocationServices.LocatedPlace;
import com.chdryra.android.mygenerallibrary.LocationServices.NearestPlacesSuggester;
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
public class NearestPlacesSuggesterGp extends GoogleLocationServiceBasic implements
        NearestPlacesSuggester,
        ResultCallback<PlaceLikelihoodBuffer> {

    private NearestPlacesListener mListener;

    public NearestPlacesSuggesterGp(GoogleApiClient client) {
        super(client);
    }

    @Override
    public void fetchSuggestions(LocatedPlace place, NearestPlacesListener listener) {
        mListener = listener;
        connectAndDoRequest();
    }

    @Override
    public void onResult(@NonNull PlaceLikelihoodBuffer placeLikelihoods) {
        ArrayList<LocatedPlace> suggestions = new ArrayList<>();
        for (PlaceLikelihood likelyPlace : placeLikelihoods) {
            Place place = likelyPlace.getPlace();
            suggestions.add(new GooglePlace(place));
        }

        placeLikelihoods.release();
        disconnect();

        mListener.onNearestPlacesFound(suggestions);
    }

    @Override
    protected void onNotConnected() {
        mListener.onNearestPlacesFound(new ArrayList<LocatedPlace>());
    }

    @Override
    protected void onNotPermissioned() {
        mListener.onNotPermissioned();
    }

    @Override
    protected void doRequestOnConnected() {
        Context context = getClient().getContext();
        if (ContextCompat.checkSelfPermission( context, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission( context, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions();
        } else {
            Places.PlaceDetectionApi.getCurrentPlace(getClient(), null).setResultCallback(this);
        }
    }
}
