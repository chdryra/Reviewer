package com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.Api;

import android.content.Context;

import com.chdryra.android.mygenerallibrary.ViewHolderAdapterFiltered;

/**
 * Created by: Rizwan Choudrey
 * On: 11/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface LocationServicesPlugin {
    LocationDetailsFetcher newDetailsFetcher();

    NearestNamesSuggester newNearestNamesSuggester();

    PlaceSearcher newPlaceSearcher();

    AddressesSuggester newAddressesSuggester(Context context);

    ViewHolderAdapterFiltered.QueryFilter newAutoCompleter(LocatedPlace place);
}
