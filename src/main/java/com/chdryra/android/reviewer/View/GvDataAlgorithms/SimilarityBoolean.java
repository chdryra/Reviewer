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
public class SimilarityBoolean implements SimilarityLevel<SimilarityBoolean> {
    private boolean mValue;

    public SimilarityBoolean(boolean value) {
        mValue = value;
    }

    @Override
    public boolean lessThanOrEqualTo(@NotNull SimilarityBoolean similarityThreshold) {
        return mValue == similarityThreshold.mValue;
    }
}
