package com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.LocationServicesGoogle.Implementation;



import com.chdryra.android.mygenerallibrary.GooglePlacesApi;
import com.chdryra.android.reviewer.LocationServices.Interfaces.AddressesProvider;
import com.chdryra.android.reviewer.LocationServices.Interfaces.LocatedPlace;
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
    public ArrayList<LocatedPlace> fetchAddresses(LatLng latLng, int num) {
        if (latLng == null || num == 0) return new ArrayList<>();

        ArrayList<String> names = GooglePlacesApi.fetchNearestNames(latLng, num);

        return convert(latLng, names.size() > 0 ? names : mGeocoder.fetchAddresses(latLng, num));
    }

    private ArrayList<LocatedPlace> convert(LatLng latLng, ArrayList<String> addresses) {
        ArrayList<LocatedPlace> suggestions = new ArrayList<>();
        for(String address : addresses) {
            suggestions.add(new GooglePlace(latLng, address));
        }

        return suggestions;
    }
}
