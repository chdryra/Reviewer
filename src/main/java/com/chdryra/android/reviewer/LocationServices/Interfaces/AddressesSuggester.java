package com.chdryra.android.reviewer.LocationServices.Interfaces;

import com.chdryra.android.reviewer.LocationServices.Implementation.LocatedPlace;

/**
 * Created by: Rizwan Choudrey
 * On: 11/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface AddressesSuggester {
    void fetchAddresses(LocatedPlace place, int number, AddressSuggestionsListener listener);
}
