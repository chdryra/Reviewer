package com.chdryra.android.reviewer.PlugIns.LocationServicesGoogle;

import com.chdryra.android.remoteapifetchers.GpNearestNamesSuggester;
import com.chdryra.android.remoteapifetchers.GpPlaceSearchResults;
import com.chdryra.android.reviewer.LocationServices.Interfaces.LocatedPlace;
import com.chdryra.android.reviewer.LocationServices.Interfaces.NearestNamesSuggester;

/**
 * Created by: Rizwan Choudrey
 * On: 11/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class NearestNamesSuggesterGp implements GpNearestNamesSuggester.SuggestionsListener, NearestNamesSuggester {
    private final GpNearestNamesSuggester mSuggester;
    private NearestNamesListener mListener;

    public NearestNamesSuggesterGp(GpNearestNamesSuggester suggester) {
        mSuggester = suggester;
    }

    @Override
    public void fetchSuggestions(LocatedPlace place, NearestNamesListener listener) {
        mListener = listener;
        mSuggester.fetchSuggestions(place.getLatLng(), this);
    }

    @Override
    public void onNearestNamesSuggested(GpPlaceSearchResults results) {
        mListener.onNearestNamesFound(GpLocatedPlaceConverter.convert(results));
    }
}
