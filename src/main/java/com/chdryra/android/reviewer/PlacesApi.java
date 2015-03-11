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
import com.chdryra.android.remoteapifetchers.GpPlaceSearchResults;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;

/**
 * Created by: Rizwan Choudrey
 * On: 11/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class PlacesApi {
    public static GpAutoCompletePredictions fetchAutoCompletePredictions(String query, LatLng
            latlng) {
        FetcherAutoComplete fetcher = new FetcherAutoComplete(query, latlng);
        fetcher.fetch();
        FetchedAutoComplete results = fetcher.getFetched();

        return results.getPredictions();
    }

    public static GpPlaceSearchResults fetchTextSearchResults(String query) throws JSONException {
        FetcherTextSearch fetcher = new FetcherTextSearch(query);
        fetcher.fetch();
        FetchedPlaceSearch results = fetcher.getFetched();

        return results.getResults();
    }

    public static GpPlaceSearchResults fetchNearestNames(LatLng latlng) {
        FetcherNearbySearch fetcher = new FetcherNearbySearch(latlng);
        fetcher.fetch();
        FetchedPlaceSearch results = fetcher.getFetched();

        return results.getResults();
    }
}
