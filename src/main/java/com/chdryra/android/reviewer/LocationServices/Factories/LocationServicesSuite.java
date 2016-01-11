package com.chdryra.android.reviewer.LocationServices.Factories;

import com.chdryra.android.reviewer.LocationServices.Implementation.ProviderGoogle.LocationServicesGoogle;
import com.chdryra.android.reviewer.LocationServices.Interfaces.LocationServicesProvider;

/**
 * Created by: Rizwan Choudrey
 * On: 11/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class LocationServicesSuite {
    public LocationServicesProvider newGoogleProvider() {
        return new LocationServicesGoogle();
    }
}
