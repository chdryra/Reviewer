package com.chdryra.android.reviewer.LocationServices.Interfaces;


import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 14/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface AddressesProvider {
    ArrayList<LocatedPlace> fetchAddresses(LatLng latLng, int num);
}
