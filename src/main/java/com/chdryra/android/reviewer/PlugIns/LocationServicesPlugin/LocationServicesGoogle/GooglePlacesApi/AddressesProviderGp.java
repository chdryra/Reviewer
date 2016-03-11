/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.LocationServicesGoogle.GooglePlacesApi;



import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.chdryra.android.reviewer.LocationServices.Implementation.AddressesSuggesterAsync;
import com.chdryra.android.reviewer.LocationServices.Interfaces.LocatedPlace;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by: Rizwan Choudrey
 * On: 14/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class AddressesProviderGp implements AddressesSuggesterAsync.AddressesProvider {
    private final static String TAG = "AddressesProviderGoogle";
    private Context mContext;

    public AddressesProviderGp(Context context) {
        mContext = context;
    }

    @Override
    public ArrayList<LocatedPlace> fetchAddresses(LatLng latLng, int num) {
        if (latLng == null || num == 0) return new ArrayList<>();
        return convert(latLng, fetch(latLng, num));
    }

    private ArrayList<LocatedPlace> convert(LatLng latLng, List<Address> addresses) {
        ArrayList<LocatedPlace> suggestions = new ArrayList<>();
        for(Address address : addresses) {
            suggestions.add(new GoogleAddress(latLng, address));
        }

        return suggestions;
    }

    private List<Address> fetch(LatLng latLng, int num) {
        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());

        List<Address> addresses = new ArrayList<>();
        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, num);
        } catch (IOException e) {
            Log.e(TAG, "IOException: trying to get address: " + latLng, e);
        }

        return addresses;
    }
}
