package com.chdryra.android.reviewer.LocationServices.Interfaces;

import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.Api.LocatedPlace;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.Api.LocationDetails;

/**
 * Created by: Rizwan Choudrey
 * On: 11/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface LocationDetailsFetcher {
    void fetchPlaceDetails(LocatedPlace place, LocationDetailsListener listener);

    interface LocationDetailsListener {
        void onPlaceDetailsFound(LocationDetails details);
    }
}
