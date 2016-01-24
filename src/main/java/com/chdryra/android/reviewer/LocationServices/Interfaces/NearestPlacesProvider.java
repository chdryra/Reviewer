package com.chdryra.android.reviewer.LocationServices.Interfaces;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 14/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface NearestPlacesProvider {
    ArrayList<LocatedPlace> fetchNearestPlaces(LocatedPlace place);
}
