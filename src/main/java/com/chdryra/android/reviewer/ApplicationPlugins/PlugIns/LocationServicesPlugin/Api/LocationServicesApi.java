/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.LocationServicesPlugin.Api;

import com.chdryra.android.mygenerallibrary.LocationServices.AddressesSuggester;
import com.chdryra.android.mygenerallibrary.LocationServices.LocationAutoCompleter;
import com.chdryra.android.mygenerallibrary.LocationServices.LocatedPlace;
import com.chdryra.android.mygenerallibrary.LocationServices.LocationDetailsFetcher;
import com.chdryra.android.mygenerallibrary.LocationServices.NearestPlacesSuggester;
import com.chdryra.android.mygenerallibrary.LocationServices.PlaceSearcher;

/**
 * Created by: Rizwan Choudrey
 * On: 14/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface LocationServicesApi {
    AddressesSuggester newAddressesSuggester();

    LocationAutoCompleter newAutoCompleter(LocatedPlace locatedPlace);

    LocationDetailsFetcher newLocationDetailsFetcher();

    NearestPlacesSuggester newNearestPlacesSuggester();

    PlaceSearcher newPlaceSearcher();
}
