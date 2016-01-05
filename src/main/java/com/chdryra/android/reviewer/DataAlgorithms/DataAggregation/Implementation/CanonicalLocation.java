/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 9 July, 2015
 */

package com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.mygenerallibrary.LatLngMidpoint;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces.CanonicalDatumMaker;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces.ItemGetter;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 09/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalLocation implements CanonicalDatumMaker<DataLocation> {
    @Override
    public DataLocation getCanonical(IdableList<? extends DataLocation> data) {
        ReviewId id = data.getReviewId();
        if (data.size() == 0) return new DatumLocation(id);

        return new DatumLocation(id, getMidLatLng(data), getLocationName(data));
    }

    private String getLocationName(IdableList<? extends DataLocation> data) {
        ItemCounter<DataLocation, String> nameCounter = getNameCounter();
        nameCounter.performCount(data);
        String maxLocation = nameCounter.getModeItem();
        int nonMax = nameCounter.getNonModeCount();
        if (nonMax > 0) maxLocation += " + " + String.valueOf(nonMax);
        return maxLocation;
    }

    private LatLng getMidLatLng(IdableList<? extends DataLocation> data) {
        LatLng[] latLngs = new LatLng[data.size()];
        for (int i = 0; i < data.size(); ++i) {
            latLngs[i] = data.getItem(i).getLatLng();
        }

        LatLngMidpoint midpoint = new LatLngMidpoint(latLngs);
        return midpoint.getGeoMidpoint();
    }

    @NonNull
    private ItemCounter<DataLocation, String> getNameCounter() {
        return new ItemCounter<>(new ItemGetter<DataLocation, String>() {
                        @Override
                        public String getData(DataLocation datum) {
                            return datum.getName();
                        }
                    });
    }
}
