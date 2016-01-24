/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.DataAggregatorsPlugin.DataAggregationDefault.Interfaces;


import com.chdryra.android.reviewer.Algorithms.DataAggregation.Interfaces.DifferenceLevel;

/**
 * Created by: Rizwan Choudrey
 * On: 25/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface DifferenceComparitor<T, S extends DifferenceLevel<?>> {
    S compare(T lhs, T rhs);
}
