/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 13 March, 2015
 */

package com.chdryra.android.reviewer;

import android.os.AsyncTask;

import com.chdryra.android.remoteapifetchers.GpPlaceDetailsResult;
import com.chdryra.android.remoteapifetchers.PlacesApi;

/**
 * Created by: Rizwan Choudrey
 * On: 13/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GpPlaceDetailsFetcher {
    private final LocatedPlace.LocationId mPlaceId;
    private final DetailsListener         mListener;

    public interface DetailsListener {
        public void onPlaceDetailsFound(GpPlaceDetailsResult details);
    }

    public GpPlaceDetailsFetcher(LocatedPlace place, DetailsListener listener) {
        LocatedPlace.LocationId placeId = place.getId();
        if (placeId.getProvider() != LocatedPlace.LocationProvider.GOOGLE) {
            throw new IllegalArgumentException("PlaceId must be a GooglePlaces Id");
        }

        if (!DataValidator.validateString(placeId.getId())) {
            throw new IllegalArgumentException("PlaceId must have length");
        }

        mPlaceId = placeId;
        mListener = listener;
    }

    public void fetchDetails() {
        new DetailsFetcherTask().execute();
    }

    /**
     * Finds details given a LocatedPlace on a separate thread using
     * {@link com.chdryra.android.mygenerallibrary.GooglePlacesApi}.
     */
    private class DetailsFetcherTask extends AsyncTask<Void, Void, GpPlaceDetailsResult> {
        @Override
        protected GpPlaceDetailsResult doInBackground(Void... params) {
            return PlacesApi.fetchDetails(mPlaceId.getId());
        }

        @Override
        protected void onPostExecute(GpPlaceDetailsResult details) {
            mListener.onPlaceDetailsFound(details);
        }
    }

}
