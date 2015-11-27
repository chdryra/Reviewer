/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 July, 2015
 */

package com.chdryra.android.reviewer.View.GvDataAggregation;

import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvImage;

/**
 * Created by: Rizwan Choudrey
 * On: 03/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparitorGvImageBitmap implements DifferenceComparitor<GvImage,
        DifferenceBoolean> {

    //Overridden
    @Override
    public DifferenceBoolean compare(GvImage lhs, GvImage rhs) {
        return new DifferenceBoolean(!lhs.getBitmap().sameAs(rhs.getBitmap()));
    }
}
