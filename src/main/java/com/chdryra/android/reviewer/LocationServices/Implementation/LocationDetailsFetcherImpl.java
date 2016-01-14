package com.chdryra.android.reviewer.LocationServices.Implementation;


import android.os.AsyncTask;

import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.Api.LocatedPlace;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.Api.LocationDetails;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.Api.LocationDetailsProvider;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.Api.LocationId;
import com.chdryra.android.reviewer.LocationServices.Interfaces
        .LocationDetailsFetcher;

/**
 * Created by: Rizwan Choudrey
 * On: 11/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class LocationDetailsFetcherImpl implements LocationDetailsFetcher {
    private LocationDetailsProvider mProvider;
    private LocationDetailsListener mListener;

    public LocationDetailsFetcherImpl(LocationDetailsProvider provider) {
        mProvider = provider;
    }

    @Override
    public void fetchPlaceDetails(LocatedPlace place, LocationDetailsListener listener) {
        mListener = listener;
        new DetailsFetcherTask(place.getId()).execute();
    }

    private class DetailsFetcherTask extends AsyncTask<Void, Void, LocationDetails> {
        private final LocationId mId;

        public DetailsFetcherTask(LocationId id) {
            mId = id;
        }

        @Override
        protected LocationDetails doInBackground(Void... params) {
            return mProvider.fetchDetails(mId);
        }

        @Override
        protected void onPostExecute(LocationDetails details) {
            mListener.onPlaceDetailsFound(details);
        }
    }
}
