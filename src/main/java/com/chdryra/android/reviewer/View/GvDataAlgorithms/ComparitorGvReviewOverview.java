/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 July, 2015
 */

package com.chdryra.android.reviewer.View.GvDataAlgorithms;

import com.chdryra.android.reviewer.View.GvDataModel.GvReviewList;

/**
 * Created by: Rizwan Choudrey
 * On: 03/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparitorGvReviewOverview implements SimilarityComparitor<GvReviewList
        .GvReviewOverview, SimilarityBoolean> {
    @Override
    public SimilarityBoolean compare(GvReviewList.GvReviewOverview lhs, GvReviewList
            .GvReviewOverview rhs) {
        return new SimilarityBoolean(lhs.getId().equals(rhs.getId()));
    }
}
