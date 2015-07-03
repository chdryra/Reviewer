/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 July, 2015
 */

package com.chdryra.android.reviewer.View.GvDataAlgorithms;

import com.chdryra.android.reviewer.View.GvDataModel.GvChildList;

/**
 * Created by: Rizwan Choudrey
 * On: 03/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparitorGvChildReview implements SimilarityComparitor<GvChildList.GvChildReview,
        SimilarityPercentage> {
    @Override
    public SimilarityPercentage compare(GvChildList.GvChildReview lhs, GvChildList.GvChildReview
            rhs) {
        StringComparitor comparitor = new StringComparitor();
        return comparitor.compare(lhs.getSubject().toLowerCase(), rhs.getSubject().toLowerCase());
    }
}
