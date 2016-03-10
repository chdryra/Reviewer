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
import com.chdryra.android.reviewer.LocationServices.Interfaces.LocationDetails;
import com.chdryra.android.reviewer.LocationServices.Interfaces.LocationDetailsFetcher;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;

/**
 * Created by: Rizwan Choudrey
 * On: 11/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class LocationDetailsFetcherGp implements LocationDetailsFetcher, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, ResultCallback<PlaceBuffer> {
    private final GoogleApiClient mClient;
    private LocatedPlace mPlace;
    private LocationDetailsListener mListener;

    public LocationDetailsFetcherGp(GoogleApiClient client) {
        mClient = client;
        mClient.registerConnectionCallbacks(this);
        mClient.registerConnectionFailedListener(this);
    }

    @Override
    public void fetchPlaceDetails(LocatedPlace place, LocationDetailsListener listener) {
        mPlace = place;
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
        mListener.onPlaceDetailsFound(new LocationDetailsGoogle());
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

        Places.GeoDataApi.getPlaceById(mClient, mPlace.getId().getId()).setResultCallback(this);
    }

    @Override
    public void onResult(@NonNull PlaceBuffer places) {
        LocationDetails details = null;
        if (places.getStatus().isSuccess() && places.getCount() > 0) {
            details = new LocationDetailsGoogle(places.get(0));
        } else{
            details = new LocationDetailsGoogle();
        }

        mListener.onPlaceDetailsFound(details);
        places.release();
    }
}
