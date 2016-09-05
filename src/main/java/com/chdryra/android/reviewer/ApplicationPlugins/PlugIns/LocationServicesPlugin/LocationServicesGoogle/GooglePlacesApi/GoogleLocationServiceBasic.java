/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.LocationServicesPlugin.LocationServicesGoogle
        .GooglePlacesApi;


import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by: Rizwan Choudrey
 * On: 11/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
abstract class GoogleLocationServiceBasic implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    private final GoogleApiClient mClient;

    protected abstract void doRequestOnConnected();

    protected abstract void onNotPermissioned();

    protected abstract void onNotConnected();

    GoogleLocationServiceBasic(GoogleApiClient client) {
        mClient = client;
        mClient.registerConnectionCallbacks(this);
        mClient.registerConnectionFailedListener(this);
    }

    GoogleApiClient getClient() {
        return mClient;
    }

    void connectAndDoRequest() {
        if (!mClient.isConnected()) {
            mClient.connect();
        } else {
            doRequestOnConnected();
        }
    }

    void disconnect() {
        mClient.disconnect();
    }

    private boolean hasPermission() {
        return ContextCompat.checkSelfPermission(mClient.getContext(), android
                .Manifest.permission
                .ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(mClient.getContext(),
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager
                        .PERMISSION_GRANTED;
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (!hasPermission()) {
            onNotPermissioned();
            return;
        }

        doRequestOnConnected();
    }

    @Override
    public void onConnectionSuspended(int i) {
        onNotConnected();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        onNotConnected();
    }
}
