/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.LocationServicesPlugin.LocationServicesGoogle.Plugin;



import android.content.Context;
import android.support.annotation.NonNull;

import com.chdryra.android.mygenerallibrary.LocationServices.AddressesSuggester;
import com.chdryra.android.mygenerallibrary.LocationServices.AddressesSuggesterAsync;
import com.chdryra.android.mygenerallibrary.LocationServices.AutoCompleterLocation;
import com.chdryra.android.mygenerallibrary.LocationServices.GooglePlacesApi.AddressesProviderGp;
import com.chdryra.android.mygenerallibrary.LocationServices.GooglePlacesApi
        .LocationDetailsFetcherGp;
import com.chdryra.android.mygenerallibrary.LocationServices.GooglePlacesApi.LocationPredicterGp;
import com.chdryra.android.mygenerallibrary.LocationServices.GooglePlacesApi
        .NearestPlacesSuggesterGp;
import com.chdryra.android.mygenerallibrary.LocationServices.GooglePlacesApi.PlaceSearcherGp;
import com.chdryra.android.mygenerallibrary.LocationServices.LocatedPlace;
import com.chdryra.android.mygenerallibrary.LocationServices.LocationDetailsFetcher;
import com.chdryra.android.mygenerallibrary.LocationServices.NearestPlacesSuggester;
import com.chdryra.android.mygenerallibrary.LocationServices.PlaceSearcher;
import com.chdryra.android.mygenerallibrary.Permissions.PermissionsManager;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.LocationServicesPlugin.Api
        .LocationServicesApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;

/**
 * Created by: Rizwan Choudrey
 * On: 14/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class GoogleLocationServicesApi implements LocationServicesApi {
    private final Context mContext;
    private final PermissionsManager mPermissions;

    public GoogleLocationServicesApi(Context context, PermissionsManager permissions) {
        mContext = context;
        mPermissions = permissions;
    }

    @Override
    public AddressesSuggester newAddressesSuggester() {
        return new AddressesSuggesterAsync(new AddressesProviderGp(mContext));
    }

    @Override
    public AutoCompleterLocation newAutoCompleter(LocatedPlace locatedPlace) {
        return new AutoCompleterLocation(locatedPlace, new LocationPredicterGp(newGeoClient()));
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
