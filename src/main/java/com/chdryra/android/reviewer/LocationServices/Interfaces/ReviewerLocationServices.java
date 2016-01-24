package com.chdryra.android.reviewer.LocationServices.Interfaces;

/**
 * Created by: Rizwan Choudrey
 * On: 14/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewerLocationServices {
    AddressesSuggester newAddressesSuggester();
    AutoCompleter newAutoCompleter(LocatedPlace locatedPlace);
    LocationDetailsFetcher newLocationDetailsFetcher();
    NearestPlacesSuggester newNearestPlacesSuggester();
    PlaceSearcher newPlaceSearcher();
}
