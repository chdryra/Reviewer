/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.LocationServicesPlugin.LocationServicesGoogle.GooglePlacesApi;

import android.location.Address;

import com.chdryra.android.reviewer.LocationServices.Implementation.LocationId;
import com.chdryra.android.reviewer.LocationServices.Interfaces.LocatedPlace;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 12/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GoogleAddress implements LocatedPlace {
    private LatLng mLatLng;
    private Address mAddress;
    private String mFormatted;

    public GoogleAddress(LatLng latLng, Address address) {
        mLatLng = latLng;
        mAddress = address;
        mFormatted = formatAddress(address);
    }

    @Override
    public LatLng getLatLng() {
        return mLatLng;
    }

    @Override
    public String getDescription() {
        return mFormatted;
    }

    @Override
    public LocationId getId() {
        return new LocationId(GoogleLocationProvider.GOOGLE, mAddress.toString());
    }

    private String formatAddress(Address address) {
        return String.format(
                "%s%s",
                // If there's a street address, add it
                address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
                // Locality is usually a city
                address.getLocality() != null ? ", " + address.getLocality() : "");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GoogleAddress)) return false;

        GoogleAddress that = (GoogleAddress) o;

        if (mLatLng != null ? !mLatLng.equals(that.mLatLng) : that.mLatLng != null) return false;
        if (mAddress != null ? !mAddress.equals(that.mAddress) : that.mAddress != null)
            return false;
        return !(mFormatted != null ? !mFormatted.equals(that.mFormatted) : that.mFormatted !=
                null);

    }

    @Override
    public int hashCode() {
        int result = mLatLng != null ? mLatLng.hashCode() : 0;
        result = 31 * result + (mAddress != null ? mAddress.hashCode() : 0);
        result = 31 * result + (mFormatted != null ? mFormatted.hashCode() : 0);
        return result;
    }
}
