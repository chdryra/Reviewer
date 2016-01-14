package com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.LocationServicesGoogle.Implementation;


import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.Api.LocationDetails;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 14/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class LocationDetailsImpl implements LocationDetails {
    private LatLng mLatLng;
    private String mDescription;

    public LocationDetailsImpl(LatLng latLng, String description) {
        mLatLng = latLng;
        mDescription = description;
    }

    @Override
    public LatLng getLatLng() {
        return mLatLng;
    }

    @Override
    public String getDescription() {
        return mDescription;
    }
}
