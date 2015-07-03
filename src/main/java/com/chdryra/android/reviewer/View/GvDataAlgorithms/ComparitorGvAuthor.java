/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 July, 2015
 */

package com.chdryra.android.reviewer.View.GvDataAlgorithms;

import com.chdryra.android.reviewer.View.GvDataModel.GvAuthorList;

/**
 * Created by: Rizwan Choudrey
 * On: 03/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparitorGvAuthor implements SimilarityComparitor<GvAuthorList.GvAuthor,
        SimilarityBoolean> {
    @Override
    public SimilarityBoolean compare(GvAuthorList.GvAuthor lhs, GvAuthorList.GvAuthor rhs) {
        return new SimilarityBoolean(lhs.getUserId().equals(rhs.getUserId()));
    }
}
