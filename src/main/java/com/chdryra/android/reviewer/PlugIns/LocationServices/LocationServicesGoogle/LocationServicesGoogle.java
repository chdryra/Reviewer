package com.chdryra.android.reviewer.PlugIns.LocationServices.LocationServicesGoogle;

import android.content.Context;

import com.chdryra.android.mygenerallibrary.ViewHolderAdapterFiltered;
import com.chdryra.android.remoteapifetchers.GpNearestNamesSuggester;
import com.chdryra.android.remoteapifetchers.GpPlaceDetailsFetcher;
import com.chdryra.android.remoteapifetchers.GpPlaceSearcher;
import com.chdryra.android.reviewer.PlugIns.LocationServices.Api.LocatedPlace;
import com.chdryra.android.reviewer.PlugIns.LocationServices.Api.LocationProvider;
import com.chdryra.android.reviewer.PlugIns.LocationServices.Api.LocationServicesPlugin;
import com.chdryra.android.reviewer.PlugIns.LocationServices.Api.AddressesSuggester;
import com.chdryra.android.reviewer.PlugIns.LocationServices.Api.LocationDetailsFetcher;
import com.chdryra.android.reviewer.PlugIns.LocationServices.Api.NearestNamesSuggester;
import com.chdryra.android.reviewer.PlugIns.LocationServices.Api.PlaceSearcher;

/**
 * Created by: Rizwan Choudrey
 * On: 11/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class LocationServicesGoogle implements LocationServicesPlugin {
    static final LocationProvider GOOGLE = new LocationProvider("Google");

    @Override
    public LocationDetailsFetcher newDetailsFetcher() {
        return new LocationDetailsFetcherGp(new GpPlaceDetailsFetcher());
    }

    @Override
    public NearestNamesSuggester newNearestNamesSuggester() {
        return new NearestNamesSuggesterGp(new GpNearestNamesSuggester());
    }

    @Override
    public PlaceSearcher newPlaceSearcher() {
        return new PlaceSearcherGp(new GpPlaceSearcher());
    }

    @Override
    public AddressesSuggester newAddressesSuggester(Context context) {
        return new AddressesSuggesterGp(context);
    }

    @Override
    public ViewHolderAdapterFiltered.QueryFilter newAutoCompleter(LocatedPlace place) {
        return new AutoCompleterGp(place);
    }
}
