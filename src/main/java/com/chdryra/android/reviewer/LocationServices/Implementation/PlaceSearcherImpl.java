package com.chdryra.android.reviewer.LocationServices.Implementation;


import android.os.AsyncTask;

import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.Api.LocatedPlace;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.Api.PlaceSearcherProvider;
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
    private PlaceSearcherListener mListener;

    public PlaceSearcherImpl(PlaceSearcherProvider fetcher) {
        mFetcher = fetcher;
    }

    @Override
    public void searchQuery(String query, PlaceSearcherListener listener) {
        mListener = listener;
        new SearchQueryTask(query).execute();
    }

    private class SearchQueryTask extends AsyncTask<Void, Void, ArrayList<LocatedPlace>> {
        private final String mQuery;

        public SearchQueryTask(String query) {
            mQuery = query;
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
