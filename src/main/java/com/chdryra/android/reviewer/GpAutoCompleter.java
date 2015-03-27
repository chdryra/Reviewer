/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 12 March, 2015
 */

package com.chdryra.android.reviewer;

import com.chdryra.android.mygenerallibrary.ViewHolderAdapterFiltered;
import com.chdryra.android.mygenerallibrary.ViewHolderDataList;
import com.chdryra.android.remoteapifetchers.GpAutoCompletePredictions;
import com.chdryra.android.remoteapifetchers.PlacesApi;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 12/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GpAutoCompleter implements ViewHolderAdapterFiltered.QueryFilter {
    private static final LocatedPlace.LocationProvider GOOGLE_PLACES = LocatedPlace
            .LocationProvider.GOOGLE;
    private final LatLng mLatLng;

    public GpAutoCompleter(LatLng latLng) {
        mLatLng = latLng;
    }

    @Override
    public ViewHolderDataList filter(String query) {
        GpAutoCompletePredictions predictions = PlacesApi.fetchAutoCompletePredictions(query,
                mLatLng);

        ViewHolderDataList<VhdLocatedPlace> filtered = new ViewHolderDataList<>();

        for (GpAutoCompletePredictions.GpPrediction prediction : predictions) {
            String description = prediction.getDescription().getDescription();
            String googleId = prediction.getPlaceId().getString();
            LocatedPlace.LocationId id = new LocatedPlace.LocationId(GOOGLE_PLACES, googleId);

            LocatedPlace place = new LocatedPlace(mLatLng, description, id);
            filtered.add(new VhdLocatedPlace(place));
        }

        return filtered;
    }
}
