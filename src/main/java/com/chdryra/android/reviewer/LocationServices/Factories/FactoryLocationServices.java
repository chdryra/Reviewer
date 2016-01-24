package com.chdryra.android.reviewer.LocationServices.Factories;

import com.chdryra.android.reviewer.LocationServices.Implementation.ReviewerLocationServicesImpl;
import com.chdryra.android.reviewer.LocationServices.Interfaces.ReviewerLocationServices;
import com.chdryra.android.reviewer.LocationServices.Interfaces.AddressesProvider;
import com.chdryra.android.reviewer.LocationServices.Interfaces.AutoCompleterProvider;
import com.chdryra.android.reviewer.LocationServices.Interfaces.LocationDetailsProvider;
import com.chdryra.android.reviewer.LocationServices.Interfaces.NearestPlacesProvider;
import com.chdryra.android.reviewer.LocationServices.Interfaces.PlaceSearcherProvider;

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
        return new ReviewerLocationServicesImpl(addressProvider, detailsProvider,
                autoCompleteProvider, nearestPlacesProvider, searchProvider);
    }
}
