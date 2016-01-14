/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 12 March, 2015
 */

package com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.LocationServicesGoogle.Implementation;

import com.chdryra.android.mygenerallibrary.VhDataList;
import com.chdryra.android.mygenerallibrary.ViewHolderAdapterFiltered;
import com.chdryra.android.mygenerallibrary.ViewHolderDataList;
import com.chdryra.android.remoteapifetchers.GpAutoCompletePredictions;
import com.chdryra.android.remoteapifetchers.PlacesApi;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.Api.LocatedPlace;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.VhdLocatedPlace;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 12/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class AutoCompleterGp implements ViewHolderAdapterFiltered.QueryFilter {
    private final LatLng mLatLng;

    public AutoCompleterGp(LocatedPlace place) {
        mLatLng = place.getLatLng();
    }

    @Override
    public ViewHolderDataList filter(String query) {
        ArrayList<LocatedPlace> places = fetchPredictions(query);
        ViewHolderDataList<VhdLocatedPlace> filtered = new VhDataList<>();
        for (LocatedPlace place : places) {
            filtered.add(new VhdLocatedPlace(place));
        }

        return filtered;
    }

    private ArrayList<LocatedPlace> fetchPredictions(String query) {
        return convert(PlacesApi.fetchAutoCompletePredictions(query, mLatLng));
    }

    private ArrayList<LocatedPlace> convert(GpAutoCompletePredictions predictions) {
        ArrayList<LocatedPlace> places = new ArrayList<>();
        for (GpAutoCompletePredictions.GpPrediction prediction : predictions) {
            String description = prediction.getDescription().getDescription();
            String googleId = prediction.getPlaceId().getString();
            places.add(new GooglePlace(mLatLng, description, googleId));
        }

        return places;
    }
}
