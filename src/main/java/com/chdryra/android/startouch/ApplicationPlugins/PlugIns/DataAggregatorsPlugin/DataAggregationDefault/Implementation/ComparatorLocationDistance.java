/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault.Implementation;



import com.chdryra.android.mygenerallibrary.Aggregation.DifferenceFloat;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.mygenerallibrary.Aggregation.DifferenceComparator;
import com.chdryra.android.mygenerallibrary.LocationUtils.LatLngDistance;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 03/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparatorLocationDistance implements DifferenceComparator<DataLocation, DifferenceFloat> {
    @Override
    public DifferenceFloat compare(DataLocation lhs, DataLocation rhs) {
        float[] res = new float[1];
        LatLng ll1 = lhs.getLatLng();
        LatLng ll2 = rhs.getLatLng();
        LatLngDistance.distanceBetween(ll1.latitude, ll1.longitude, ll2.latitude, ll2.longitude, res);

        return new DifferenceFloat(res[0]);
    }
}
