/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 27 March, 2015
 */

package com.chdryra.android.reviewer.View.Utils;

import com.chdryra.android.remoteapifetchers.GpAutoCompletePredictions;
import com.chdryra.android.remoteapifetchers.GpPlaceDetailsResult;
import com.chdryra.android.remoteapifetchers.GpPlaceSearchResults;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 27/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GpLocatedPlaceConverter {
    private static final LocatedPlace.LocationProvider GOOGLE_PLACES = LocatedPlace
            .LocationProvider.GOOGLE;

    public static LocatedPlace convert(GpPlaceDetailsResult details) {
        LatLng latlng = details.getGeometry().getLatLng();
        String description = details.getName().getString();
        String googleId = details.getPlaceId().getString();
        LocatedPlace.LocationId id = new LocatedPlace.LocationId(GOOGLE_PLACES, googleId);

        return new LocatedPlace(latlng, description, id);
    }

    public static ArrayList<LocatedPlace> convert(LatLng latlng, GpAutoCompletePredictions
            predictions) {
        ArrayList<LocatedPlace> places = new ArrayList<>();
        for (GpAutoCompletePredictions.GpPrediction prediction : predictions) {
            String description = prediction.getDescription().getDescription();
            String googleId = prediction.getPlaceId().getString();
            LocatedPlace.LocationId id = new LocatedPlace.LocationId(GOOGLE_PLACES, googleId);

            places.add(new LocatedPlace(latlng, description, id));
        }

        return places;
    }

    public static ArrayList<LocatedPlace> convert(GpPlaceSearchResults results) {
        ArrayList<LocatedPlace> places = new ArrayList<>();
        for (GpPlaceSearchResults.GpPlaceSearchResult result : results) {
            LatLng latlng = result.getGeometry().getLatLng();
            String description = result.getName().getString();
            String googleId = result.getPlaceId().getString();
            LocatedPlace.LocationId id = new LocatedPlace.LocationId(GOOGLE_PLACES, googleId);

            places.add(new LocatedPlace(latlng, description, id));
        }

        return places;
    }

}
