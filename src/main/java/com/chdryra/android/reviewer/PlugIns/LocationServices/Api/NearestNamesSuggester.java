package com.chdryra.android.reviewer.PlugIns.LocationServices.Api;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 11/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface NearestNamesSuggester {
    void fetchSuggestions(LocatedPlace place, NearestNamesListener listener);

    interface NearestNamesListener {
        void onNearestNamesFound(ArrayList<LocatedPlace> suggestions);
    }
}
