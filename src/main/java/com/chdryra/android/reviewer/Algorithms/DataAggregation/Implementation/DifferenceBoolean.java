/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 July, 2015
 */

package com.chdryra.android.reviewer.Algorithms.DataAggregation.Implementation;

import com.chdryra.android.reviewer.Algorithms.DataAggregation.Interfaces.DifferenceLevel;

import org.jetbrains.annotations.NotNull;

/**
 * Created by: Rizwan Choudrey
 * On: 03/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DifferenceBoolean implements DifferenceLevel<DifferenceBoolean> {
    private boolean mValue;

    public DifferenceBoolean(boolean value) {
        mValue = value;
    }

    @Override
    public boolean lessThanOrEqualTo(@NotNull DifferenceBoolean differenceThreshold) {
        return mValue == differenceThreshold.mValue;
    }
}
