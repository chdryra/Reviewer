package com.chdryra.android.reviewer.PlugIns.LocationServices.Api;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 11/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface AddressesSuggester {
    void fetchAddresses(LocatedPlace place, int number, AddressSuggestionsListener listener);

    interface AddressSuggestionsListener {
        void onAddressSuggestionsFound(ArrayList<LocatedPlace> addresses);
    }
}
