/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.DataAggregatorsPlugin.DataAggregationDefault.Implementation;



import com.chdryra.android.reviewer.Algorithms.DataAggregation.Implementation.DifferenceFloat;
import com.chdryra.android.reviewer.Algorithms.DataAggregation.Implementation.DifferenceLocation;
import com.chdryra.android.reviewer.Algorithms.DataAggregation.Implementation.DifferencePercentage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.PlugIns.DataAggregatorsPlugin.DataAggregationDefault
        .Interfaces.DifferenceComparitor;

/**
 * Created by: Rizwan Choudrey
 * On: 15/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparitorLocation implements
        DifferenceComparitor<DataLocation, DifferenceLocation> {
    private final ComparitorLocationName mNameComparitor;
    private final ComparitorLocationDistance mLocationComparitor;

    public ComparitorLocation(ComparitorLocationDistance locationComparitor,
                              ComparitorLocationName nameComparitor) {
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
