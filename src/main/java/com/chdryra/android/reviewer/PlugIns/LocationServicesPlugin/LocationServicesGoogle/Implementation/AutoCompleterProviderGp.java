/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.LocationServicesGoogle.Implementation;



import com.chdryra.android.remoteapifetchers.GpAutoCompletePredictions;
import com.chdryra.android.remoteapifetchers.PlacesApi;
import com.chdryra.android.reviewer.LocationServices.Interfaces.LocatedPlace;
import com.chdryra.android.reviewer.LocationServices.Interfaces.AutoCompleterProvider;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 14/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class AutoCompleterProviderGp implements AutoCompleterProvider {
    @Override
    public ArrayList<LocatedPlace> fetchPredictions(String query, LatLng latLng) {
        return convert(latLng, PlacesApi.fetchAutoCompletePredictions(query, latLng));
    }

    private ArrayList<LocatedPlace> convert(LatLng latLng, GpAutoCompletePredictions predictions) {
        ArrayList<LocatedPlace> places = new ArrayList<>();
        for (GpAutoCompletePredictions.GpPrediction prediction : predictions) {
            String description = prediction.getDescription().getDescription();
            String googleId = prediction.getPlaceId().getString();
            places.add(new GooglePlace(latLng, description, googleId));
        }

        return places;
    }
}
