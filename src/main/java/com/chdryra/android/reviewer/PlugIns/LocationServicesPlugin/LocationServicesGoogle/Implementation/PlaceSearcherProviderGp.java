package com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.LocationServicesGoogle.Implementation;


import com.chdryra.android.remoteapifetchers.PlacesApi;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.Api.LocatedPlace;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.Api.PlaceSearcherProvider;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 14/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PlaceSearcherProviderGp implements PlaceSearcherProvider {
    @Override
    public ArrayList<LocatedPlace> fetchResults(String searchQuery) {
        return GpLocatedPlaceConverter.convert(PlacesApi.fetchTextSearchResults(searchQuery));
    }
}
