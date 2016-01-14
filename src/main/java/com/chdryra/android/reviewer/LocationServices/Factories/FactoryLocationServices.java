package com.chdryra.android.reviewer.LocationServices.Factories;

import com.chdryra.android.reviewer.LocationServices.Implementation.ReviewerLocationServicesImpl;
import com.chdryra.android.reviewer.LocationServices.Interfaces.ReviewerLocationServices;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.Api.AddressesProvider;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.Api.AutoCompleterProvider;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.Api.LocationDetailsProvider;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.Api.NearestPlacesProvider;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.Api.PlaceSearcherProvider;

/**
 * Created by: Rizwan Choudrey
 * On: 14/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryLocationServices {
    public ReviewerLocationServices newServices(AddressesProvider addressProvider,
                                         LocationDetailsProvider detailsProvider,
                                         AutoCompleterProvider autoCompleteProvider,
                                         NearestPlacesProvider nearestPlacesProvider,
                                         PlaceSearcherProvider searchProvider) {
        return new ReviewerLocationServicesImpl(addressProvider, detailsProvider, autoCompleteProvider, nearestPlacesProvider, searchProvider);
    }
}
