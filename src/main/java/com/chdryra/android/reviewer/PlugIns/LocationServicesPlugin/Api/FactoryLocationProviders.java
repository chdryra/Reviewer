package com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.Api;

import com.chdryra.android.reviewer.LocationServices.Interfaces.AddressesProvider;
import com.chdryra.android.reviewer.LocationServices.Interfaces.AutoCompleterProvider;
import com.chdryra.android.reviewer.LocationServices.Interfaces.LocationDetailsProvider;
import com.chdryra.android.reviewer.LocationServices.Interfaces.NearestPlacesProvider;
import com.chdryra.android.reviewer.LocationServices.Interfaces.PlaceSearcherProvider;

/**
 * Created by: Rizwan Choudrey
 * On: 24/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface FactoryLocationProviders {
    LocationDetailsProvider newLocationDetailsProvider();

    NearestPlacesProvider newNearestPlacesProvider();

    PlaceSearcherProvider newPlaceSearcherProvider();

    AddressesProvider newAddressesProvider();

    AutoCompleterProvider newAutoCompleterProvider();
}
