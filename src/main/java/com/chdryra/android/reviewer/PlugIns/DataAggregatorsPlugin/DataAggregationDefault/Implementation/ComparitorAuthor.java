/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 July, 2015
 */

package com.chdryra.android.reviewer.PlugIns.DataAggregatorsPlugin.DataAggregationDefault.Implementation;



import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.Algorithms.DataAggregation.Implementation.DifferenceBoolean;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.PlugIns.DataAggregatorsPlugin.DataAggregationDefault
        .Interfaces.DifferenceComparitor;

/**
 * Created by: Rizwan Choudrey
 * On: 03/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparitorAuthor implements DifferenceComparitor<DataAuthor, DifferenceBoolean> {
    @Override
    public DifferenceBoolean compare(@NonNull DataAuthor lhs, @NonNull DataAuthor rhs) {
        boolean sameId = lhs.getUserId().equals(rhs.getUserId());
        boolean sameName = lhs.getName().equals(rhs.getName());

        if (sameId && !sameName) {
            throw new RuntimeException("Authors have same ID but different names!");
        }

        if (!sameId && sameName) {
            throw new RuntimeException("Authors have same name but different IDs!");
        }

        return new DifferenceBoolean(!sameId);
    }
}
