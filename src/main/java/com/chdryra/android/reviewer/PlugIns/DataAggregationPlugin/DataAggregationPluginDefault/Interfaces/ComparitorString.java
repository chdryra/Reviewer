package com.chdryra.android.reviewer.PlugIns.DataAggregationPlugin.DataAggregationPluginDefault.Interfaces;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.Algorithms.DataAggregation.Implementation
        .DifferencePercentage;

/**
 * Created by: Rizwan Choudrey
 * On: 05/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface ComparitorString extends DifferenceComparitor<String, DifferencePercentage> {
    @Override
    DifferencePercentage compare(@NonNull String lhs, @NonNull String rhs);
}
