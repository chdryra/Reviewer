package com.chdryra.android.reviewer.LocationServices.Implementation;


import android.os.AsyncTask;

import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.Api.LocatedPlace;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.Api.NearestPlacesProvider;
import com.chdryra.android.reviewer.LocationServices.Interfaces.NearestPlacesSuggester;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 11/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class NearestPlacesSuggesterImpl implements NearestPlacesSuggester {
    private NearestPlacesProvider mProvider;
    private NearestPlacesListener mListener;

    public NearestPlacesSuggesterImpl(NearestPlacesProvider provider) {
        mProvider = provider;
    }

    @Override
    public void fetchSuggestions(LocatedPlace place, NearestPlacesListener listener) {
        mListener = listener;
        new NamesFetcherTask(place).execute();
    }

    private class NamesFetcherTask extends AsyncTask<Void, Void, ArrayList<LocatedPlace>> {
        private final LocatedPlace mPlace;

        public NamesFetcherTask(LocatedPlace place) {
            mPlace = place;
        }

        @Override
        protected ArrayList<LocatedPlace> doInBackground(Void... params) {
            return mProvider.fetchNearestPlaces(mPlace);
        }

        @Override
        protected void onPostExecute(ArrayList<LocatedPlace> details) {
            mListener.onNearestPlacesFound(details);
        }
    }
}
