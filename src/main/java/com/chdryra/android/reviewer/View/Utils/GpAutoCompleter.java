/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 12 March, 2015
 */

package com.chdryra.android.reviewer.View.Utils;

import com.chdryra.android.mygenerallibrary.ViewHolderAdapterFiltered;
import com.chdryra.android.mygenerallibrary.ViewHolderDataList;
import com.chdryra.android.remoteapifetchers.GpAutoCompletePredictions;
import com.chdryra.android.remoteapifetchers.PlacesApi;
import com.chdryra.android.reviewer.View.GvDataModel.VhdLocatedPlace;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 12/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GpAutoCompleter implements ViewHolderAdapterFiltered.QueryFilter {
    private final LatLng mLatLng;

    //Constructors
    public GpAutoCompleter(LatLng latLng) {
        mLatLng = latLng;
    }

    //Overridden
    @Override
    public ViewHolderDataList filter(String query) {
        GpAutoCompletePredictions predictions = PlacesApi.fetchAutoCompletePredictions(query,
                mLatLng);

        ArrayList<LocatedPlace> places = GpLocatedPlaceConverter.convert(mLatLng, predictions);
        ViewHolderDataList<VhdLocatedPlace> filtered = new ViewHolderDataList<>();
        for (LocatedPlace place : places) {
            filtered.add(new VhdLocatedPlace(place));
        }

        return filtered;
    }
}
