/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.LocationServicesPlugin
        .GoogleLocationServices;


import android.content.Context;
import android.support.annotation.NonNull;

import com.chdryra.android.corelibrary.LocationServices.AddressesSuggester;
import com.chdryra.android.corelibrary.LocationServices.AddressesSuggesterAsync;
import com.chdryra.android.corelibrary.LocationServices.GooglePlacesApi.AddressesProviderGp;
import com.chdryra.android.corelibrary.LocationServices.GooglePlacesApi.LocationDetailsFetcherGp;
import com.chdryra.android.corelibrary.LocationServices.GooglePlacesApi.LocationPredicterGp;
import com.chdryra.android.corelibrary.LocationServices.GooglePlacesApi.NearestPlacesSuggesterGp;
import com.chdryra.android.corelibrary.LocationServices.GooglePlacesApi.PlaceSearcherGp;
import com.chdryra.android.corelibrary.LocationServices.LocatedPlace;
import com.chdryra.android.corelibrary.LocationServices.LocationAutoCompleter;
import com.chdryra.android.corelibrary.LocationServices.LocationDetailsFetcher;
import com.chdryra.android.corelibrary.LocationServices.NearestPlacesSuggester;
import com.chdryra.android.corelibrary.LocationServices.PlaceSearcher;
import com.chdryra.android.corelibrary.Permissions.PermissionsManager;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.LocationServicesPlugin.Api
        .LocationServices;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;

/**
 * Created by: Rizwan Choudrey
 * On: 14/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class GoogleLocationServices implements LocationServices {
    private final Context mContext;
    private final PermissionsManager mPermissions;

    public GoogleLocationServices(Context context, PermissionsManager permissions) {
        mContext = context;
        mPermissions = permissions;
    }

    @Override
    public AddressesSuggester newAddressesSuggester() {
        return new AddressesSuggesterAsync(new AddressesProviderGp(mContext));
    }

    @Override
    public LocationAutoCompleter newAutoCompleter(LocatedPlace locatedPlace) {
        return new LocationAutoCompleter(locatedPlace, new LocationPredicterGp(newGeoClient()));
    }

    @Override
    public LocationDetailsFetcher newLocationDetailsFetcher() {
        return new LocationDetailsFetcherGp(newGeoClient(), mPermissions);
    }

    @Override
    public NearestPlacesSuggester newNearestPlacesSuggester() {
        GoogleApiClient client = new GoogleApiClient
                .Builder(mContext)
                .addApi(Places.PLACE_DETECTION_API)
                .build();

        return new NearestPlacesSuggesterGp(client, mPermissions);
    }

    @Override
    public PlaceSearcher newPlaceSearcher() {
        return new PlaceSearcherGp(newGeoClient(), mPermissions);
    }

    @NonNull
    private GoogleApiClient newGeoClient() {
        return new GoogleApiClient
                .Builder(mContext)
                .addApi(Places.GEO_DATA_API)
                .build();
    }
}
