package com.chdryra.android.reviewer.DataAggregation.Implementation;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataAggregation.Interfaces.DifferenceComparitor;

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
