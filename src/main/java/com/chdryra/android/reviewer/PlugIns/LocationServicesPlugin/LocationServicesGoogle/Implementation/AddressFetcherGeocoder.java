/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.LocationServicesGoogle.Implementation;



import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.util.Log;

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
public class AddressFetcherGeocoder {
    private final static String TAG = "SuggesterGeocoder";
    private Context mContext;

    public AddressFetcherGeocoder(Context context) {
        mContext = context;
    }

    public ArrayList<String> fetchAddresses(LatLng latLng, int num) {
        return convert(fetch(latLng, num));
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

    @NonNull
    private ArrayList<String> convert(List<Address> addresses) {
        ArrayList<String> results = new ArrayList<>();
        for(Address address : addresses) {
            results.add(formatAddress(address));
        }

        return results;
    }

    private String formatAddress(Address address) {
        return String.format(
                "%s%s",
                // If there's a street address, add it
                address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
                // Locality is usually a city
                address.getLocality() != null ? ", " + address.getLocality() : "");
    }
}
