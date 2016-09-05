/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault.Implementation;



import com.chdryra.android.reviewer.Algorithms.DataAggregation.Implementation.DifferencePercentage;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault
        .Interfaces.ComparitorString;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault
        .Interfaces.DifferenceComparitor;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault
        .Interfaces.ItemGetter;


/**
 * Created by: Rizwan Choudrey
 * On: 07/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparitorStringable<T> implements DifferenceComparitor<T, DifferencePercentage> {
    private final ComparitorString mComparitor;
    private final ItemGetter<T, String> mGetter;

    ComparitorStringable(ComparitorString comparitor, ItemGetter<T, String> getter) {
        mComparitor = comparitor;
        mGetter = getter;
    }

    @Override
    public DifferencePercentage compare(T lhs, T rhs) {
        return mComparitor.compare(mGetter.getItem(lhs), mGetter.getItem(rhs));
    }
}
