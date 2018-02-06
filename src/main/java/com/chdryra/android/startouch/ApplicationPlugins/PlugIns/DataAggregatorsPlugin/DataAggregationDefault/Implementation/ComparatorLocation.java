/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault.Implementation;



import com.chdryra.android.mygenerallibrary.Aggregation.DifferenceFloat;
import com.chdryra.android.mygenerallibrary.Aggregation.DifferenceLocation;
import com.chdryra.android.mygenerallibrary.Aggregation.DifferencePercentage;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.mygenerallibrary.Aggregation.DifferenceComparator;

/**
 * Created by: Rizwan Choudrey
 * On: 15/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparatorLocation implements
        DifferenceComparator<DataLocation, DifferenceLocation> {
    private final ComparatorLocationName mNameComparitor;
    private final ComparatorLocationDistance mLocationComparitor;

    public ComparatorLocation(ComparatorLocationDistance locationComparitor,
                              ComparatorLocationName nameComparitor) {
        mLocationComparitor = locationComparitor;
        mNameComparitor = nameComparitor;
    }

    @Override
    public DifferenceLocation compare(DataLocation lhs, DataLocation rhs) {
        DifferenceFloat distance = mLocationComparitor.compare(lhs, rhs);
        DifferencePercentage nameDifference = mNameComparitor.compare(lhs, rhs);
        return new DifferenceLocation(distance, nameDifference);
    }
}
