package com.chdryra.android.reviewer.LocationServices.Interfaces;

import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.Api.LocatedPlace;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 11/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface AddressesSuggester {
    void fetchAddresses(LatLng latLng, int number, AddressSuggestionsListener listener);

    interface AddressSuggestionsListener {
        void onAddressSuggestionsFound(ArrayList<LocatedPlace> addresses);
    }
}
