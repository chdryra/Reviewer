package com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation
        .DifferencePercentage;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces.DifferenceComparitor;

/**
 * Created by: Rizwan Choudrey
 * On: 05/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface ComparitorString extends DifferenceComparitor<String, DifferencePercentage> {
    @Override
    DifferencePercentage compare(@NonNull String lhs, @NonNull String rhs);
}
