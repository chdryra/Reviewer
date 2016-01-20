package com.chdryra.android.reviewer.Algorithms.DataAggregation.Implementation;

import com.chdryra.android.reviewer.Algorithms.DataAggregation.Interfaces.DifferenceLevel;

import org.jetbrains.annotations.NotNull;

/**
 * Created by: Rizwan Choudrey
 * On: 15/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DifferenceLocation implements DifferenceLevel<DifferenceLocation> {
    private DifferenceFloat mDistanceDifference;
    private DifferencePercentage mNameDifference;

    public DifferenceLocation(DifferenceFloat distanceDifference, DifferencePercentage
            nameDifference) {
        mDistanceDifference = distanceDifference;
        mNameDifference = nameDifference;
    }

    @Override
    public boolean lessThanOrEqualTo(@NotNull DifferenceLocation differenceThreshold) {
        return mDistanceDifference.lessThanOrEqualTo(differenceThreshold.mDistanceDifference) &&
                mNameDifference.lessThanOrEqualTo(differenceThreshold.mNameDifference);
    }
}
