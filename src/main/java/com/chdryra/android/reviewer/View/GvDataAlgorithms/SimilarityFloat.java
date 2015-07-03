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
public class SimilarityFloat implements SimilarityLevel<SimilarityFloat> {
    private float mValue;

    public SimilarityFloat(float value) {
        mValue = value;
    }

    @Override
    public boolean lessThanOrEqualTo(@NotNull SimilarityFloat similarityThreshold) {
        return mValue <= similarityThreshold.mValue;
    }
}
