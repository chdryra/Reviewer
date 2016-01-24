package com.chdryra.android.reviewer.LocationServices.Implementation;


import android.os.AsyncTask;

import com.chdryra.android.reviewer.LocationServices.Interfaces.LocatedPlace;
import com.chdryra.android.reviewer.LocationServices.Interfaces.PlaceSearcherProvider;
import com.chdryra.android.reviewer.LocationServices.Interfaces
        .PlaceSearcher;

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
    public void searchQuery(String query, PlaceSearcherListener listener) {
        new SearchQueryTask(query, listener).execute();
    }

    private class SearchQueryTask extends AsyncTask<Void, Void, ArrayList<LocatedPlace>> {
        private final String mQuery;
        private final PlaceSearcherListener mListener;

        public SearchQueryTask(String query, PlaceSearcherListener listener) {
            mQuery = query;
            mListener = listener;
        }

        @Override
        protected ArrayList<LocatedPlace> doInBackground(Void... params) {
            return mFetcher.fetchResults(mQuery);
        }

        @Override
        protected void onPostExecute(ArrayList<LocatedPlace> details) {
            mListener.onSearchResultsFound(details);
        }
    }
}
