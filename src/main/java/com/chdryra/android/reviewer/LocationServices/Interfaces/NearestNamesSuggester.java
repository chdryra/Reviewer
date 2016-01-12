package com.chdryra.android.reviewer.LocationServices.Interfaces;

/**
 * Created by: Rizwan Choudrey
 * On: 11/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface NearestNamesSuggester {
    void fetchSuggestions(LocatedPlace place, NearestNamesSuggestionsListener listener);
}
