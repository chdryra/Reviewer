package com.chdryra.android.reviewer.LocationServices.Interfaces;

import com.chdryra.android.reviewer.LocationServices.Implementation.LocatedPlace;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 11/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface PlaceSearchResultsListener {
    void onSearchResultsFound(ArrayList<LocatedPlace> results);
}
