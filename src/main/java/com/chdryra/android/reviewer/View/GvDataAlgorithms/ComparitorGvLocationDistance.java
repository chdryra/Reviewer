/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 July, 2015
 */

package com.chdryra.android.reviewer.View.GvDataAlgorithms;

import android.location.Location;

import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 03/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparitorGvLocationDistance implements DifferenceComparitor<GvLocationList
        .GvLocation, DifferenceFloat> {

    @Override
    public DifferenceFloat compare(GvLocationList.GvLocation lhs, GvLocationList.GvLocation
            rhs) {
        float[] res = new float[1];
        LatLng ll1 = lhs.getLatLng();
        LatLng ll2 = rhs.getLatLng();
        Location.distanceBetween(ll1.latitude, ll1.longitude, ll2.latitude, ll2.longitude, res);

        return new DifferenceFloat(res[0]);
    }
}
