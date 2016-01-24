package com.chdryra.android.reviewer.LocationServices.Implementation;


import android.os.AsyncTask;

import com.chdryra.android.reviewer.LocationServices.Interfaces.LocatedPlace;
import com.chdryra.android.reviewer.LocationServices.Interfaces.LocationDetails;
import com.chdryra.android.reviewer.LocationServices.Interfaces.LocationDetailsProvider;
import com.chdryra.android.reviewer.LocationServices.Interfaces
        .LocationDetailsFetcher;

/**
 * Created by: Rizwan Choudrey
 * On: 11/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class LocationDetailsFetcherImpl implements LocationDetailsFetcher {
    private LocationDetailsProvider mProvider;

    public LocationDetailsFetcherImpl(LocationDetailsProvider provider) {
        mProvider = provider;
    }

    @Override
    public void fetchPlaceDetails(LocatedPlace place, LocationDetailsListener listener) {
        new DetailsFetcherTask(place.getId(), listener).execute();
    }

    private class DetailsFetcherTask extends AsyncTask<Void, Void, LocationDetails> {
        private final LocationId mId;
        private final LocationDetailsListener mListener;

        public DetailsFetcherTask(LocationId id, LocationDetailsListener listener) {
            mId = id;
            mListener = listener;
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
