/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Algorithms.DataAggregation.Implementation;

import com.chdryra.android.reviewer.Algorithms.DataAggregation.Interfaces.DifferenceLevel;

import org.jetbrains.annotations.NotNull;

/**
 * Created by: Rizwan Choudrey
 * On: 03/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DifferenceFloat implements DifferenceLevel<DifferenceFloat> {
    private float mValue;

    public DifferenceFloat(float value) {
        mValue = value;
    }

    @Override
    public boolean lessThanOrEqualTo(@NotNull DifferenceFloat differenceThreshold) {
        return mValue <= differenceThreshold.mValue;
    }
}
