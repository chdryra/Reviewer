/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 July, 2015
 */

package com.chdryra.android.reviewer.View.GvDataAggregation;

import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvAuthor;

/**
 * Created by: Rizwan Choudrey
 * On: 03/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparitorGvAuthor implements DifferenceComparitor<GvAuthor,
        DifferenceBoolean> {
    //Overridden
    @Override
    public DifferenceBoolean compare(GvAuthor lhs, GvAuthor rhs) {
        boolean sameId = lhs.getUserId().equals(rhs.getUserId());
        boolean sameName = lhs.getName().equals(rhs.getName());
        if (sameId && !sameName) {
            throw new RuntimeException("GvAuthors have same ID but different names!");
        }

        return new DifferenceBoolean(!sameId);
    }
}
