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
public class PlaceDetailsFetcher {
    private final String          mPlaceId;
    private final DetailsListener mListener;

    public interface DetailsListener {
        public void onPlaceDetailsFound(GpPlaceDetailsResult details);
    }

    public PlaceDetailsFetcher(String placeId, DetailsListener listener) {
        if (!DataValidator.validateString(placeId)) {
            throw new IllegalArgumentException("PlaceId must have length");
        }

        mPlaceId = placeId;
        mListener = listener;
    }

    public void fetchDetails() {
        new DetailsFetcherTask().execute();
    }

    /**
     * Finds nearest addresses given a LatLng on a separate thread using
     * {@link com.chdryra.android.mygenerallibrary.GooglePlacesApi}.
     */
    private class DetailsFetcherTask extends AsyncTask<Void, Void, GpPlaceDetailsResult> {
        @Override
        protected GpPlaceDetailsResult doInBackground(Void... params) {
            return PlacesApi.fetchDetails(mPlaceId);
        }

        @Override
        protected void onPostExecute(GpPlaceDetailsResult details) {
            mListener.onPlaceDetailsFound(details);
        }
    }

}
