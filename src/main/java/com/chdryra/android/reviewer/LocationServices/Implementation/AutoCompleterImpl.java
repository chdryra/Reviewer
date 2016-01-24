/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 12 March, 2015
 */

package com.chdryra.android.reviewer.LocationServices.Implementation;

import com.chdryra.android.mygenerallibrary.VhDataList;
import com.chdryra.android.mygenerallibrary.ViewHolderDataList;
import com.chdryra.android.reviewer.LocationServices.Interfaces.AutoCompleter;
import com.chdryra.android.reviewer.LocationServices.Interfaces.AutoCompleterProvider;
import com.chdryra.android.reviewer.LocationServices.Interfaces.LocatedPlace;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.VhdLocatedPlace;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 12/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class AutoCompleterImpl implements AutoCompleter {
    private final LatLng mLatLng;
    private AutoCompleterProvider mProvider;

    public AutoCompleterImpl(LocatedPlace place, AutoCompleterProvider provider) {
        mLatLng = place.getLatLng();
        mProvider = provider;
    }

    @Override
    public ViewHolderDataList filter(String query) {
        ArrayList<LocatedPlace> places = mProvider.fetchPredictions(query, mLatLng);
        ViewHolderDataList<VhdLocatedPlace> filtered = new VhDataList<>();
        for (LocatedPlace place : places) {
            filtered.add(new VhdLocatedPlace(place));
        }

        return filtered;
    }
}
