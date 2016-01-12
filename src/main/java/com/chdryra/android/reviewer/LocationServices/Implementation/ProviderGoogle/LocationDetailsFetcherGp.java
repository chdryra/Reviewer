package com.chdryra.android.reviewer.LocationServices.Implementation.ProviderGoogle;

import com.chdryra.android.remoteapifetchers.GpPlaceDetailsFetcher;
import com.chdryra.android.remoteapifetchers.GpPlaceDetailsResult;
import com.chdryra.android.reviewer.LocationServices.Interfaces.LocatedPlace;
import com.chdryra.android.reviewer.LocationServices.Implementation.LocationDetailsImpl;
import com.chdryra.android.reviewer.LocationServices.Interfaces.LocationDetailsFetcher;
import com.chdryra.android.reviewer.LocationServices.Interfaces.LocationDetailsListener;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 11/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class LocationDetailsFetcherGp implements GpPlaceDetailsFetcher.DetailsListener, LocationDetailsFetcher {
    private GpPlaceDetailsFetcher mFetcher;
    private LocationDetailsListener mListener;

    public LocationDetailsFetcherGp(GpPlaceDetailsFetcher fetcher) {
        mFetcher = fetcher;
    }

    @Override
    public void fetchPlaceDetails(LocatedPlace place, LocationDetailsListener listener) {
        mListener = listener;
        mFetcher.fetchDetails(place.getId().getId(), this);
    }

    @Override
    public void onPlaceDetailsFound(GpPlaceDetailsResult details) {
        LatLng latLng = details.getGeometry().getLatLng();
        String name = details.getName().getString();
        mListener.onPlaceDetailsFound(new LocationDetailsImpl(latLng, name));
    }
}
