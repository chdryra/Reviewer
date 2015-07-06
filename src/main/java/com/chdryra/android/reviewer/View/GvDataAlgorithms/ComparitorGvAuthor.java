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
public class ComparitorGvAuthor implements DifferenceComparitor<GvAuthorList.GvAuthor,
        DifferenceBoolean> {
    @Override
    public DifferenceBoolean compare(GvAuthorList.GvAuthor lhs, GvAuthorList.GvAuthor rhs) {
        boolean sameId = lhs.getUserId().equals(rhs.getUserId());
        boolean sameName = lhs.getName().equals(rhs.getName());
        if (sameId && !sameName) {
            throw new RuntimeException("GvAuthors have same ID but different names!");
        }

        return new DifferenceBoolean(sameId);
    }
}
