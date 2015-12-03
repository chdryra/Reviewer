/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 July, 2015
 */

package com.chdryra.android.reviewer.DataAggregation.Implementation;

import com.chdryra.android.reviewer.DataAggregation.Interfaces.DifferenceLevel;

import org.jetbrains.annotations.NotNull;

/**
 * Created by: Rizwan Choudrey
 * On: 03/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DifferenceFloat implements DifferenceLevel<DifferenceFloat> {
    private float mValue;

    //Constructors
    public DifferenceFloat(float value) {
        mValue = value;
    }

    //Overridden
    @Override
    public boolean lessThanOrEqualTo(@NotNull DifferenceFloat differenceThreshold) {
        return mValue <= differenceThreshold.mValue;
    }
}
