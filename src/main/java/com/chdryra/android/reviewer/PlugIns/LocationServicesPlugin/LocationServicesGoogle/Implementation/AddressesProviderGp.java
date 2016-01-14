package com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.LocationServicesGoogle.Implementation;



import com.chdryra.android.mygenerallibrary.GooglePlacesApi;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.Api.AddressesProvider;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 14/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class AddressesProviderGp implements AddressesProvider {
    private AddressFetcherGeocoder mGeocoder;

    public AddressesProviderGp(AddressFetcherGeocoder geocoder) {
        mGeocoder = geocoder;
    }

    @Override
    public ArrayList<String> fetchAddresses(LatLng latLng, int num) {
        ArrayList<String> names = new ArrayList<String>();
        if (latLng == null || num == 0) return names;

        names = GooglePlacesApi.fetchNearestNames(latLng, num);

        return names.size() > 0 ? names : mGeocoder.fetchAddresses(latLng, num);
    }
}
