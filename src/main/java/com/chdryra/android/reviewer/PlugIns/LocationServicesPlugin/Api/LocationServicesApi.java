/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.Api;

import com.chdryra.android.reviewer.LocationServices.Interfaces.AddressesSuggester;
import com.chdryra.android.reviewer.LocationServices.Interfaces.AutoCompleter;
import com.chdryra.android.reviewer.LocationServices.Interfaces.LocatedPlace;
import com.chdryra.android.reviewer.LocationServices.Interfaces.LocationDetailsFetcher;
import com.chdryra.android.reviewer.LocationServices.Interfaces.NearestPlacesSuggester;
import com.chdryra.android.reviewer.LocationServices.Interfaces.PlaceSearcher;

/**
 * Created by: Rizwan Choudrey
 * On: 14/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface LocationServicesApi {
    AddressesSuggester newAddressesSuggester();

    AutoCompleter newAutoCompleter(LocatedPlace locatedPlace);

    LocationDetailsFetcher newLocationDetailsFetcher();

    NearestPlacesSuggester newNearestPlacesSuggester();

    PlaceSearcher newPlaceSearcher();
}
