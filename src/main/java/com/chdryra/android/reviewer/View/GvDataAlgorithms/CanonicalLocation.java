/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 9 July, 2015
 */

package com.chdryra.android.reviewer.View.GvDataAlgorithms;

import com.chdryra.android.mygenerallibrary.LatLngMidpoint;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 09/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalLocation implements CanonicalDatumMaker<GvLocationList.GvLocation> {
    @Override
    public GvLocationList.GvLocation getCanonical(GvDataList<GvLocationList.GvLocation> data) {
        if (data.size() == 0) return new GvLocationList.GvLocation(data.getReviewId(), null, "");

        LatLng[] latLngs = new LatLng[data.size()];
        for (int i = 0; i < data.size(); ++i) {
            latLngs[i] = data.getItem(i).getLatLng();
        }

        LatLngMidpoint midpoint = new LatLngMidpoint(latLngs);
        LatLng mid = midpoint.getGeoMidpoint();

        DatumCounter<GvLocationList.GvLocation, String> counter = new DatumCounter<>(data,
                new DataGetter<GvLocationList.GvLocation, String>() {
                    @Override
                    public String getData(GvLocationList.GvLocation datum) {
                        return datum.getName();
                    }
                });

        String maxLocation = counter.getMaxItem();
        int nonMax = counter.getNonMaxCount();
        if (nonMax > 0) maxLocation += " + " + String.valueOf(nonMax);

        return new GvLocationList.GvLocation(data.getReviewId(), mid, maxLocation);
    }
}
