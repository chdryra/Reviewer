package com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.LocationServicesGoogle.Plugin;

import android.content.Context;

import com.chdryra.android.reviewer.LocationServices.Interfaces.AddressesProvider;
import com.chdryra.android.reviewer.LocationServices.Interfaces.AutoCompleterProvider;
import com.chdryra.android.reviewer.LocationServices.Interfaces.LocationDetailsProvider;
import com.chdryra.android.reviewer.LocationServices.Interfaces.NearestPlacesProvider;
import com.chdryra.android.reviewer.LocationServices.Interfaces.PlaceSearcherProvider;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.Api.FactoryLocationProviders;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.LocationServicesGoogle.Implementation.AddressFetcherGeocoder;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.LocationServicesGoogle.Implementation.AddressesProviderGp;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.LocationServicesGoogle.Implementation.AutoCompleterProviderGp;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.LocationServicesGoogle.Implementation.LocationDetailsProviderGp;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.LocationServicesGoogle.Implementation.NearestPlacesProviderGp;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.LocationServicesGoogle.Implementation.PlaceSearcherProviderGp;

/**
 * Created by: Rizwan Choudrey
 * On: 11/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryLocationProvidersGp implements FactoryLocationProviders {
    private Context mContext;

    public FactoryLocationProvidersGp(Context context) {
        mContext = context;
    }

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
    public AddressesProvider newAddressesProvider() {
        return new AddressesProviderGp(new AddressFetcherGeocoder(mContext));
    }

    @Override
    public AutoCompleterProvider newAutoCompleterProvider() {
        return new AutoCompleterProviderGp();
    }
}
