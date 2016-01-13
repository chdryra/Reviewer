package com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.Api;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface LocatedPlace {
    LatLng getLatLng();

    String getDescription();

    LocationId getId();
}
