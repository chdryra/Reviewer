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
public class SimilarityPercentage implements SimilarityLevel<SimilarityPercentage> {
    private double mPercentage;

    public SimilarityPercentage(double percentage) {
        if (percentage < 0 || percentage > 1) {
            throw new IllegalArgumentException("Percentage should be between 0 and 1!");
        }

        mPercentage = percentage;
    }

    @Override
    public boolean lessThanOrEqualTo(@NotNull SimilarityPercentage similarityThreshold) {
        return mPercentage <= similarityThreshold.mPercentage;
    }
}
