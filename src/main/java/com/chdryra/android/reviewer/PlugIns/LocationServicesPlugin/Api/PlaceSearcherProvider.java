package com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.Api;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 14/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface PlaceSearcherProvider {
    ArrayList<LocatedPlace> fetchResults(String searchQuery);
}
