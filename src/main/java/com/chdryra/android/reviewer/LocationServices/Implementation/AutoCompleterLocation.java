/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.LocationServices.Implementation;

import com.chdryra.android.mygenerallibrary.VhDataList;
import com.chdryra.android.mygenerallibrary.ViewHolderDataList;
import com.chdryra.android.reviewer.LocationServices.Interfaces.AutoCompleter;
import com.chdryra.android.reviewer.LocationServices.Interfaces.LocatedPlace;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.VhdLocatedPlace;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 12/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class AutoCompleterLocation implements AutoCompleter<VhdLocatedPlace> {
    private final LatLng mLatLng;
    private LocationPredicter mProvider;

    public interface LocationPredicter {
        ArrayList<LocatedPlace> fetchPredictions(String query, LatLng latLng);

        void disconnect();
    }

    public AutoCompleterLocation(LocatedPlace place, LocationPredicter provider) {
        mLatLng = place.getLatLng();
        mProvider = provider;
    }

    @Override
    public ViewHolderDataList<VhdLocatedPlace> filter(String query) {
        ArrayList<LocatedPlace> places = mProvider.fetchPredictions(query, mLatLng);
        ViewHolderDataList<VhdLocatedPlace> filtered = new VhDataList<>();
        for (LocatedPlace place : places) {
            filtered.add(new VhdLocatedPlace(place));
        }

        return filtered;
    }

    @Override
    public void disconnectFromProvider() {
        mProvider.disconnect();
    }
}
