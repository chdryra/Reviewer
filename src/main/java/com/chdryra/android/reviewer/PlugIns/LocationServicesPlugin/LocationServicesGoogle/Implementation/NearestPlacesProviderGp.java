/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.LocationServicesGoogle.Implementation;


import com.chdryra.android.remoteapifetchers.PlacesApi;
import com.chdryra.android.reviewer.LocationServices.Interfaces.LocatedPlace;
import com.chdryra.android.reviewer.LocationServices.Interfaces.NearestPlacesProvider;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 14/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class NearestPlacesProviderGp implements NearestPlacesProvider {
    @Override
    public ArrayList<LocatedPlace> fetchNearestPlaces(LocatedPlace place) {
        return GpLocatedPlaceConverter.convert(PlacesApi.fetchNearestNames(place.getLatLng()));
    }
}
