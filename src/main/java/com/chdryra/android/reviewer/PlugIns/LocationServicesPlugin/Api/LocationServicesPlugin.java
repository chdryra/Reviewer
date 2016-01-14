package com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.Api;

import android.content.Context;

/**
 * Created by: Rizwan Choudrey
 * On: 11/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface LocationServicesPlugin {
    LocationDetailsProvider newLocationDetailsProvider();

    NearestPlacesProvider newNearestPlacesProvider();

    PlaceSearcherProvider newPlaceSearcherProvider();

    AddressesProvider newAddressesProvider(Context context);

    AutoCompleterProvider newAutoCompleterProvider();
}
