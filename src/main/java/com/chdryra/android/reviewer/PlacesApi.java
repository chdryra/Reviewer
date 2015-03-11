/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 11 March, 2015
 */

package com.chdryra.android.reviewer;

import com.chdryra.android.remoteapifetchers.FetchedAutoComplete;
import com.chdryra.android.remoteapifetchers.FetchedPlaceSearch;
import com.chdryra.android.remoteapifetchers.FetcherAutoComplete;
import com.chdryra.android.remoteapifetchers.FetcherNearbySearch;
import com.chdryra.android.remoteapifetchers.FetcherTextSearch;
import com.chdryra.android.remoteapifetchers.GpAutoCompletePredictions;
import com.chdryra.android.remoteapifetchers.GpDescription;
import com.chdryra.android.remoteapifetchers.GpGeometry;
import com.chdryra.android.remoteapifetchers.GpPlaceSearchResults;
import com.chdryra.android.remoteapifetchers.GpString;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 11/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class PlacesApi {
    /**
     * Find autocomplete suggestions given a query and LatLng.
     *
     * @param query:  user query
     * @param latlng: LatLng around which to search
     * @return ArrayList<String>: a list of suggestions.
     */
    public static ArrayList<String> fetchAutoCompleteSuggestions(String query, LatLng latlng) {
        FetcherAutoComplete fetcher = new FetcherAutoComplete(query, latlng);
        fetcher.fetch();
        FetchedAutoComplete results = fetcher.getFetched();

        ArrayList<String> descriptions = new ArrayList<>();
        GpAutoCompletePredictions predictions = results.getPredictions();
        if (predictions.isValid()) {
            for (GpAutoCompletePredictions.GpPrediction pred : predictions) {
                GpDescription description = pred.getDescription();
                if (description.isValid()) descriptions.add(description.getDescription());
            }
        }

        return descriptions;
    }

    /**
     * Find a geographical position given a query.
     *
     * @param query: user query
     * @return LatLng: most likely LatLng given the query.
     */
    public static LatLng fetchLatLng(String query) throws JSONException {
        FetcherTextSearch fetcher = new FetcherTextSearch(query);
        fetcher.fetch();
        FetchedPlaceSearch results = fetcher.getFetched();

        LatLng latLng = null;
        if (results.isValid()) {
            GpPlaceSearchResults list = results.getResults();
            GpGeometry geo = list.getItem(0).getGeometry();
            if (geo.isValid()) latLng = geo.getLatLng();
        }

        return latLng;
    }

    /**
     * Find nearby places suggestions given a LatLng and number of places requested.
     *
     * @param latlng: LatLng around which to search
     * @param number: number of nearest names to find
     * @return ArrayList<String>: a list of suggestions.
     */
    public static ArrayList<String> fetchNearestNames(LatLng latlng, int number) {
        FetcherNearbySearch fetcher = new FetcherNearbySearch(latlng);
        fetcher.fetch();
        FetchedPlaceSearch results = fetcher.getFetched();

        ArrayList<String> names = new ArrayList<>();

        if (results.isValid()) {
            GpPlaceSearchResults list = results.getResults();

            for (int i = 0; i < Math.min(list.size(), number); ++i) {
                GpPlaceSearchResults.GpPlaceSearchResult result = list.getItem(i);
                GpString name = result.getName();
                if (name.isValid()) names.add(name.getString());
            }
        }

        return names;
    }
}
