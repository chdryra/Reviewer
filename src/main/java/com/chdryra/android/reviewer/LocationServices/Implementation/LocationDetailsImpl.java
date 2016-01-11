package com.chdryra.android.reviewer.LocationServices.Implementation;

import com.chdryra.android.reviewer.LocationServices.Interfaces.LocationDetails;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 11/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class LocationDetailsImpl implements LocationDetails {
    private LatLng mLatLng;
    private String mName;

    public LocationDetailsImpl(LatLng latLng, String name) {
        mLatLng = latLng;
        mName = name;
    }

    @Override
    public LatLng getLatLng() {
        return mLatLng;
    }

    @Override
    public String getName() {
        return mName;
    }
}
