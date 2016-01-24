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
import com.chdryra.android.reviewer.LocationServices.Interfaces.NearestPlacesProvider;
import com.chdryra.android.reviewer.LocationServices.Interfaces.NearestPlacesSuggester;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 11/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class NearestPlacesSuggesterImpl implements NearestPlacesSuggester {
    private NearestPlacesProvider mProvider;

    public NearestPlacesSuggesterImpl(NearestPlacesProvider provider) {
        mProvider = provider;
    }

    @Override
    public void fetchSuggestions(LocatedPlace place, NearestPlacesListener listener) {
        new NamesFetcherTask(place, listener).execute();
    }

    private class NamesFetcherTask extends AsyncTask<Void, Void, ArrayList<LocatedPlace>> {
        private final LocatedPlace mPlace;
        private final NearestPlacesListener mListener;

        public NamesFetcherTask(LocatedPlace place, NearestPlacesListener listener) {
            mPlace = place;
            mListener = listener;
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
