/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 12 March, 2015
 */

package com.chdryra.android.reviewer;

import android.os.AsyncTask;

import com.chdryra.android.remoteapifetchers.GpPlaceSearchResults;
import com.chdryra.android.remoteapifetchers.PlacesApi;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 12/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GpNearestNamesSuggester {
    private static final LocatedPlace.Provider GOOGLE_PLACES = LocatedPlace.Provider.GOOGLE;
    private final LatLng              mLatLng;
    private final SuggestionsListener mListener;

    public interface SuggestionsListener {
        public void onNearestNamesSuggested(ArrayList<LocatedPlace> addresses);
    }

    public GpNearestNamesSuggester(LatLng latlng, SuggestionsListener listener) {
        mLatLng = latlng;
        mListener = listener;
    }

    public void fetchSuggestions(int number) {
        new NamesFinderTask().execute(number);
    }

    /**
     * Finds nearest addresses given a LatLng on a separate thread using
     * {@link com.chdryra.android.mygenerallibrary.GooglePlacesApi}.
     */
    private class NamesFinderTask extends AsyncTask<Integer, Void, ArrayList<LocatedPlace>> {
        @Override
        protected ArrayList<LocatedPlace> doInBackground(Integer... params) {
            Integer numberToGet = params[0];
            ArrayList<LocatedPlace> places = new ArrayList<>();
            if (mLatLng == null || numberToGet == 0) return places;

            GpPlaceSearchResults results = PlacesApi.fetchNearestNames(mLatLng);

            for (GpPlaceSearchResults.GpPlaceSearchResult result : results) {
                LatLng latlng = result.getGeometry().getLatLng();
                String description = result.getName().getString();
                String googleId = result.getPlaceId().getString();
                LocatedPlace.LocationId id = new LocatedPlace.LocationId(GOOGLE_PLACES, googleId);

                places.add(new LocatedPlace(latlng, description, id));
            }

            return places;
        }

        @Override
        protected void onPostExecute(ArrayList<LocatedPlace> addresses) {
            mListener.onNearestNamesSuggested(addresses);
        }
    }
}
