package com.chdryra.android.reviewer.LocationServices.Interfaces;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 11/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface NearestPlacesSuggester {
    void fetchSuggestions(LocatedPlace place, NearestPlacesListener listener);

    interface NearestPlacesListener {
        void onNearestPlacesFound(ArrayList<LocatedPlace> suggestions);
    }
}
