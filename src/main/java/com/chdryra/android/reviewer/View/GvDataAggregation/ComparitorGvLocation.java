package com.chdryra.android.reviewer.View.GvDataAggregation;

import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;

/**
 * Created by: Rizwan Choudrey
 * On: 15/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparitorGvLocation implements
        DifferenceComparitor<GvLocationList.GvLocation, DifferenceLocation> {
    private static final ComparitorGvLocationName NAME_COMP = new ComparitorGvLocationName();
    private static final ComparitorGvLocationDistance LOC_COMP = new ComparitorGvLocationDistance();

    //Overridden
    @Override
    public DifferenceLocation compare(GvLocationList.GvLocation lhs, GvLocationList.GvLocation
            rhs) {
        DifferenceFloat distance = LOC_COMP.compare(lhs, rhs);
        DifferencePercentage nameDifference = NAME_COMP.compare(lhs, rhs);
        return new DifferenceLocation(distance, nameDifference);
    }
}
