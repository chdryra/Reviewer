/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 July, 2015
 */

package com.chdryra.android.reviewer.View.GvDataAlgorithms;

import org.jetbrains.annotations.NotNull;

/**
 * Created by: Rizwan Choudrey
 * On: 03/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class SimilarityDate implements SimilarityLevel<SimilarityDate> {
    private DateBucket mDateLevel;

    public enum DateBucket {DAY, MONTH, YEAR, NONE}

    public SimilarityDate(DateBucket dateLevel) {
        mDateLevel = dateLevel;
    }

    @Override
    public boolean lessThanOrEqualTo(@NotNull SimilarityDate similarityThreshold) {
        return mDateLevel.ordinal() <= similarityThreshold.mDateLevel.ordinal();
    }
}
