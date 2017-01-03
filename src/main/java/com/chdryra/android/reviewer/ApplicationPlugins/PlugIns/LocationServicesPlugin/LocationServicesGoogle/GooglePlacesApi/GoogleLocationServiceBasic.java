/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.LocationServicesPlugin.LocationServicesGoogle
        .GooglePlacesApi;


import android.os.Bundle;
import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.reviewer.Application.Implementation.PermissionResult;
import com.chdryra.android.reviewer.Application.Interfaces.PermissionsSuite;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: 11/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
abstract class GoogleLocationServiceBasic implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, PermissionsSuite.PermissionsCallback {
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

    @Override
    public void onConnected(Bundle bundle) {
        PermissionsSuite permissions
                = AppInstanceAndroid.getInstance(mClient.getContext()).getPermissions();
        if (!permissions.hasPermissions(PermissionsSuite.Permission.LOCATION)) {
            requestPermissions();
        } else {
            doRequestOnConnected();
        }
    }

    protected void requestPermissions() {
        PermissionsSuite permissions
                = AppInstanceAndroid.getInstance(mClient.getContext()).getPermissions();
        permissions.requestPermissions(this, PermissionsSuite.Permission.LOCATION);
    }

    @Override
    public void onPermissionsResult(List<PermissionResult> results) {
        if(results.size() == 1 && results.get(0).isPermission(PermissionsSuite.Permission.LOCATION)
                && results.get(0).isGranted()) {
            doRequestOnConnected();
        } else {
            onNotPermissioned();
        }
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
