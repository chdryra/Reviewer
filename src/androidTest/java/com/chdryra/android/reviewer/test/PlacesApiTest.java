/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 11 March, 2015
 */

package com.chdryra.android.reviewer.test;

import android.location.Location;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.remoteapifetchers.GpAutoCompletePredictions;
import com.chdryra.android.remoteapifetchers.GpPlaceSearchResults;
import com.chdryra.android.reviewer.PlacesApi;
import com.google.android.gms.maps.model.LatLng;

import junit.framework.TestCase;

import org.json.JSONException;

/**
 * Created by: Rizwan Choudrey
 * On: 11/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class PlacesApiTest extends TestCase {
    private final static LatLng LATLNG  = new LatLng(51.5072, -0.1275);
    private final static String QUERY   = "Charing Cross";
    private final static String ACQUERY = "Ch";

    @SmallTest
    public void testFetchAutoCompleteSuggestions() {
        GpAutoCompletePredictions res = PlacesApi.fetchAutoCompletePredictions(ACQUERY, LATLNG);
        assertNotNull(res);
        assertTrue(res.size() > 0);
    }

    @SmallTest
    public void testFetchTextSearch() {
        try {
            GpPlaceSearchResults res = PlacesApi.fetchTextSearchResults(QUERY);
            LatLng latLng = res.getItem(0).getGeometry().getLatLng();
            assertNotNull(latLng);
            float[] results = new float[1];
            Location.distanceBetween(LATLNG.latitude, LATLNG.longitude, latLng.latitude,
                    latLng.longitude, results);
            assertTrue(results[0] < 100);
        } catch (JSONException e) {
            e.printStackTrace();
            fail();
        }
    }

    @SmallTest
    public void testFetchNearestNames() {
        GpPlaceSearchResults res = PlacesApi.fetchNearestNames(LATLNG);
        assertNotNull(res);
        assertTrue(res.size() > 0);
    }
}
