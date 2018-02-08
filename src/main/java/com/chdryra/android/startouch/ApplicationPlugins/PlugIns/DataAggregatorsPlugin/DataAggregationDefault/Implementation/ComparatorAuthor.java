/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault.Implementation;



import android.support.annotation.NonNull;

import com.chdryra.android.mygenerallibrary.Aggregation.DifferenceBoolean;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.NamedAuthor;
import com.chdryra.android.mygenerallibrary.Aggregation.DifferenceComparator;

/**
 * Created by: Rizwan Choudrey
 * On: 03/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparatorAuthor implements DifferenceComparator<NamedAuthor, DifferenceBoolean> {
    @Override
    public DifferenceBoolean compare(@NonNull NamedAuthor lhs, @NonNull NamedAuthor rhs) {
        boolean sameId = lhs.getAuthorId().equals(rhs.getAuthorId());
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