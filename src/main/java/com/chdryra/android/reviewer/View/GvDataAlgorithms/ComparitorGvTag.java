/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 July, 2015
 */

package com.chdryra.android.reviewer.View.GvDataAlgorithms;

import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;

/**
 * Created by: Rizwan Choudrey
 * On: 03/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparitorGvTag implements SimilarityComparitor<GvTagList.GvTag,
        SimilarityPercentage> {

    @Override
    public SimilarityPercentage compare(GvTagList.GvTag lhs, GvTagList.GvTag rhs) {
        StringComparitor comparitor = new StringComparitor();
        return comparitor.compare(lhs.get().toLowerCase(), rhs.get().toLowerCase());
    }
}
