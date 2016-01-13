package com.chdryra.android.reviewer.PlugIns.LocationServices.Api;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 11/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface PlaceSearcher {
    void searchQuery(String query, PlaceSearcherListener listener);

    interface PlaceSearcherListener {
        void onSearchResultsFound(ArrayList<LocatedPlace> results);
    }
}
