package com.chdryra.android.reviewer.View.DataAggregation;

import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvLocation;

/**
 * Created by: Rizwan Choudrey
 * On: 15/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparitorGvLocation implements
        DifferenceComparitor<GvLocation, DifferenceLocation> {
    private static final ComparitorGvLocationName NAME_COMP = new ComparitorGvLocationName();
    private static final ComparitorGvLocationDistance LOC_COMP = new ComparitorGvLocationDistance();

    //Overridden
    @Override
    public DifferenceLocation compare(GvLocation lhs, GvLocation
            rhs) {
        DifferenceFloat distance = LOC_COMP.compare(lhs, rhs);
        DifferencePercentage nameDifference = NAME_COMP.compare(lhs, rhs);
        return new DifferenceLocation(distance, nameDifference);
    }
}
