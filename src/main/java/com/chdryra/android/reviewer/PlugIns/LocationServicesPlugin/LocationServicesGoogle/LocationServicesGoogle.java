package com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.LocationServicesGoogle;

import android.content.Context;

import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.Api.AddressesProvider;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.Api.AutoCompleterProvider;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.Api.LocationDetailsProvider;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.Api.LocationServicesPlugin;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.Api.NearestPlacesProvider;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.Api.PlaceSearcherProvider;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.LocationServicesGoogle
        .Implementation.AddressFetcherGeocoder;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.LocationServicesGoogle
        .Implementation.AddressesProviderGp;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.LocationServicesGoogle.Implementation.AutoCompleterProviderGp;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.LocationServicesGoogle
        .Implementation.LocationDetailsProviderGp;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.LocationServicesGoogle
        .Implementation.NearestPlacesProviderGp;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.LocationServicesGoogle
        .Implementation.PlaceSearcherProviderGp;

/**
 * Created by: Rizwan Choudrey
 * On: 11/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class LocationServicesGoogle implements LocationServicesPlugin {
    @Override
    public LocationDetailsProvider newLocationDetailsProvider() {
        return new LocationDetailsProviderGp();
    }

    @Override
    public NearestPlacesProvider newNearestPlacesProvider() {
        return new NearestPlacesProviderGp();
    }

    @Override
    public PlaceSearcherProvider newPlaceSearcherProvider() {
        return new PlaceSearcherProviderGp();
    }

    @Override
    public AddressesProvider newAddressesProvider(Context context) {
        return new AddressesProviderGp(new AddressFetcherGeocoder(context));
    }

    @Override
    public AutoCompleterProvider newAutoCompleterProvider() {
        return new AutoCompleterProviderGp();
    }
}
