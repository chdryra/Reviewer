/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Algorithms.DataAggregation.Implementation;

import com.chdryra.android.reviewer.Algorithms.DataAggregation.Interfaces.DataAggregatorParams;

/**
 * Created by: Rizwan Choudrey
 * On: 05/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class DataAggregatorParamsImpl implements DataAggregatorParams{
    private DifferenceBoolean mBoolean;
    private DifferencePercentage mPercentage;
    private DifferenceDate mDate;
    private DifferenceLocation mLocation;

    public DataAggregatorParamsImpl(DifferenceBoolean bool, DifferencePercentage percentage,
                                    DifferenceDate date, DifferenceLocation location) {
        mBoolean = bool;
        mPercentage = percentage;
        mDate = date;
        mLocation = location;
    }

    @Override
    public DifferenceBoolean getSimilarBoolean() {
        return mBoolean;
    }

    @Override
    public DifferencePercentage getSimilarPercentage() {
        return mPercentage;
    }

    @Override
    public DifferenceDate getSimilarDate() {
        return mDate;
    }

    @Override
    public DifferenceLocation getSimilarLocation() {
        return mLocation;
    }
}
