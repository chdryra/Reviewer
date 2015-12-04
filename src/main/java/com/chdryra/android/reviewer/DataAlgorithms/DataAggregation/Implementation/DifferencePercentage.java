/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 July, 2015
 */

package com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation;

import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces.DifferenceLevel;

import org.jetbrains.annotations.NotNull;

/**
 * Created by: Rizwan Choudrey
 * On: 03/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DifferencePercentage implements DifferenceLevel<DifferencePercentage> {
    private double mPercentage;

    //Constructors
    public DifferencePercentage(double percentage) {
        if (percentage < 0 || percentage > 1) {
            throw new IllegalArgumentException("Percentage should be between 0 and 1!");
        }

        mPercentage = percentage;
    }

    //Overridden
    @Override
    public boolean lessThanOrEqualTo(@NotNull DifferencePercentage differenceThreshold) {
        return mPercentage <= differenceThreshold.mPercentage;
    }
}
