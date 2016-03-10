/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.LocationServices.Implementation;


import android.os.AsyncTask;

import com.chdryra.android.reviewer.LocationServices.Interfaces.LocatedPlace;
import com.chdryra.android.reviewer.LocationServices.Interfaces.PlaceSearcherProvider;
import com.chdryra.android.reviewer.LocationServices.Interfaces
        .PlaceSearcher;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 11/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PlaceSearcherImpl implements PlaceSearcher {
    private PlaceSearcherProvider mFetcher;

    public PlaceSearcherImpl(PlaceSearcherProvider fetcher) {
        mFetcher = fetcher;
    }

    @Override
    public void searchQuery(String query, LatLng nearLatLng, PlaceSearcherListener listener) {
        new SearchQueryTask(query, nearLatLng, listener).execute();
    }

    private class SearchQueryTask extends AsyncTask<Void, Void, ArrayList<LocatedPlace>> {
        private final LatLng mLatLng;
        private final String mQuery;
        private final PlaceSearcherListener mListener;

        public SearchQueryTask(String query, LatLng nearLatLng, PlaceSearcherListener listener) {
            mQuery = query;
            mLatLng = nearLatLng;
            mListener = listener;
        }

        @Override
        protected ArrayList<LocatedPlace> doInBackground(Void... params) {
            return mFetcher.fetchResults(mQuery, mLatLng);
        }

        @Override
        protected void onPostExecute(ArrayList<LocatedPlace> details) {
            mListener.onSearchResultsFound(details);
        }
    }
}
