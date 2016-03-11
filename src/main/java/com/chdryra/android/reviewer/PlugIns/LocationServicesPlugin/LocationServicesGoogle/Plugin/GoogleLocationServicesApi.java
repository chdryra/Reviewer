/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.LocationServicesGoogle.Plugin;


import android.content.Context;
import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.LocationServices.Implementation.AddressesSuggesterAsync;
import com.chdryra.android.reviewer.LocationServices.Implementation.AutoCompleterAsync;
import com.chdryra.android.reviewer.LocationServices.Interfaces.AddressesSuggester;
import com.chdryra.android.reviewer.LocationServices.Interfaces.AutoCompleter;
import com.chdryra.android.reviewer.LocationServices.Interfaces.LocatedPlace;
import com.chdryra.android.reviewer.LocationServices.Interfaces.LocationDetailsFetcher;
import com.chdryra.android.reviewer.LocationServices.Interfaces.NearestPlacesSuggester;
import com.chdryra.android.reviewer.LocationServices.Interfaces.PlaceSearcher;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.Api.LocationServicesApi;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.LocationServicesGoogle
        .GooglePlacesApi.AddressesProviderGp;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.LocationServicesGoogle
        .GooglePlacesApi.AutoCompleterGp;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.LocationServicesGoogle
        .GooglePlacesApi.LocationDetailsFetcherGp;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.LocationServicesGoogle
        .GooglePlacesApi.NearestPlacesSuggesterGp;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.LocationServicesGoogle.GooglePlacesApi.PlaceSearcherGp;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;

/**
 * Created by: Rizwan Choudrey
 * On: 14/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class GoogleLocationServicesApi implements LocationServicesApi {
    private Context mContext;

    public GoogleLocationServicesApi(Context context) {
        mContext = context;
    }

    @Override
    public AddressesSuggester newAddressesSuggester() {
        return new AddressesSuggesterAsync(new AddressesProviderGp(mContext));
    }

    @Override
    public AutoCompleter newAutoCompleter(LocatedPlace locatedPlace) {
        return new AutoCompleterAsync(locatedPlace, new AutoCompleterGp(newGeoClient()));
    }

    @Override
    public LocationDetailsFetcher newLocationDetailsFetcher() {
        return new LocationDetailsFetcherGp(newGeoClient());
    }

    @Override
    public NearestPlacesSuggester newNearestPlacesSuggester() {
        GoogleApiClient client = new GoogleApiClient
                .Builder(mContext)
                .addApi(Places.PLACE_DETECTION_API)
                .build();

        return new NearestPlacesSuggesterGp(client);
    }

    @Override
    public PlaceSearcher newPlaceSearcher() {
        return new PlaceSearcherGp(newGeoClient());
    }

    @NonNull
    private GoogleApiClient newGeoClient() {
        return new GoogleApiClient
                .Builder(mContext)
                .addApi(Places.GEO_DATA_API)
                .build();
    }
}
