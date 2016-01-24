/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.LocationServices.Implementation;

import com.chdryra.android.reviewer.LocationServices.Interfaces.AddressesSuggester;
import com.chdryra.android.reviewer.LocationServices.Interfaces.AutoCompleter;
import com.chdryra.android.reviewer.LocationServices.Interfaces.LocationDetailsFetcher;
import com.chdryra.android.reviewer.LocationServices.Interfaces.NearestPlacesSuggester;
import com.chdryra.android.reviewer.LocationServices.Interfaces.PlaceSearcher;
import com.chdryra.android.reviewer.LocationServices.Interfaces.ReviewerLocationServices;
import com.chdryra.android.reviewer.LocationServices.Interfaces.AddressesProvider;
import com.chdryra.android.reviewer.LocationServices.Interfaces.AutoCompleterProvider;
import com.chdryra.android.reviewer.LocationServices.Interfaces.LocatedPlace;
import com.chdryra.android.reviewer.LocationServices.Interfaces.LocationDetailsProvider;
import com.chdryra.android.reviewer.LocationServices.Interfaces.NearestPlacesProvider;
import com.chdryra.android.reviewer.LocationServices.Interfaces.PlaceSearcherProvider;

/**
 * Created by: Rizwan Choudrey
 * On: 14/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewerLocationServicesImpl implements ReviewerLocationServices {
    AddressesProvider mAddressProvider;
    LocationDetailsProvider mDetailsProvider;
    AutoCompleterProvider mAutoCompleteProvider;
    NearestPlacesProvider mNearestPlacesProvider;
    PlaceSearcherProvider mSearchProvider;

    public ReviewerLocationServicesImpl(AddressesProvider addressProvider,
                                        LocationDetailsProvider detailsProvider,
                                        AutoCompleterProvider autoCompleteProvider,
                                        NearestPlacesProvider nearestPlacesProvider,
                                        PlaceSearcherProvider searchProvider) {
        mAddressProvider = addressProvider;
        mDetailsProvider = detailsProvider;
        mAutoCompleteProvider = autoCompleteProvider;
        mNearestPlacesProvider = nearestPlacesProvider;
        mSearchProvider = searchProvider;
    }

    @Override
    public AddressesSuggester newAddressesSuggester() {
        return new AddressesSuggesterImpl(mAddressProvider);
    }

    @Override
    public AutoCompleter newAutoCompleter(LocatedPlace locatedPlace) {
        return new AutoCompleterImpl(locatedPlace, mAutoCompleteProvider);
    }

    @Override
    public LocationDetailsFetcher newLocationDetailsFetcher() {
        return new LocationDetailsFetcherImpl(mDetailsProvider);
    }

    @Override
    public NearestPlacesSuggester newNearestPlacesSuggester() {
        return new NearestPlacesSuggesterImpl(mNearestPlacesProvider);
    }

    @Override
    public PlaceSearcher newPlaceSearcher() {
        return new PlaceSearcherImpl(mSearchProvider);
    }
}
