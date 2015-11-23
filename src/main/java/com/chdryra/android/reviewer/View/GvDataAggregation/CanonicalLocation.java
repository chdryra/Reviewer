/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 9 July, 2015
 */

package com.chdryra.android.reviewer.View.GvDataAggregation;

import com.chdryra.android.mygenerallibrary.LatLngMidpoint;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvLocation;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 09/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalLocation implements CanonicalDatumMaker<GvLocation> {
    //Overridden
    @Override
    public GvLocation getCanonical(GvDataList<GvLocation> data) {
        if (data.size() == 0) return new GvLocation(data.getGvReviewId(), null, "");

        LatLng[] latLngs = new LatLng[data.size()];
        for (int i = 0; i < data.size(); ++i) {
            latLngs[i] = data.getItem(i).getLatLng();
        }

        LatLngMidpoint midpoint = new LatLngMidpoint(latLngs);
        LatLng mid = midpoint.getGeoMidpoint();

        DatumCounter<GvLocation, String> counter = new DatumCounter<>(data,
                new DataGetter<GvLocation, String>() {
                    //Overridden
                    @Override
                    public String getData(GvLocation datum) {
                        return datum.getName();
                    }
                });

        String maxLocation = counter.getMaxItem();
        int nonMax = counter.getNonMaxCount();
        if (nonMax > 0) maxLocation += " + " + String.valueOf(nonMax);

        return new GvLocation(data.getGvReviewId(), mid, maxLocation);
    }
}
