/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.LocationServicesPlugin.Api;

import com.chdryra.android.corelibrary.LocationServices.AddressesSuggester;
import com.chdryra.android.corelibrary.LocationServices.LocatedPlace;
import com.chdryra.android.corelibrary.LocationServices.LocationAutoCompleter;
import com.chdryra.android.corelibrary.LocationServices.LocationDetailsFetcher;
import com.chdryra.android.corelibrary.LocationServices.NearestPlacesSuggester;
import com.chdryra.android.corelibrary.LocationServices.PlaceSearcher;

/**
 * Created by: Rizwan Choudrey
 * On: 14/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface LocationServices {
    AddressesSuggester newAddressesSuggester();

    LocationAutoCompleter newAutoCompleter(LocatedPlace locatedPlace);

    LocationDetailsFetcher newLocationDetailsFetcher();

    NearestPlacesSuggester newNearestPlacesSuggester();

    PlaceSearcher newPlaceSearcher();
}
