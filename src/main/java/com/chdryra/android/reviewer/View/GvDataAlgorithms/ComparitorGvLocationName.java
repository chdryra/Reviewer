/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 July, 2015
 */

package com.chdryra.android.reviewer.View.GvDataAlgorithms;

import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;

/**
 * Created by: Rizwan Choudrey
 * On: 03/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparitorGvLocationName implements DifferenceComparitor<GvLocationList.GvLocation,
        DifferencePercentage> {

    @Override
    public DifferencePercentage compare(GvLocationList.GvLocation lhs, GvLocationList
            .GvLocation rhs) {
        ComparitorShortString comparitor = new ComparitorShortString();
        return comparitor.compare(lhs.getName().toLowerCase(), rhs.getName().toLowerCase());
    }
}
