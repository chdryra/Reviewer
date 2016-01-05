package com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces;

import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation.DifferenceBoolean;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation.DifferenceDate;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation
        .DifferenceLocation;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation
        .DifferencePercentage;

/**
 * Created by: Rizwan Choudrey
 * On: 05/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface DataAggregatorParams {
    DifferenceBoolean getSimilarBoolean();
    DifferencePercentage getSimilarPercentage();
    DifferenceDate getSimilarDate();
    DifferenceLocation getSimilarLocation();
    ComparitorString getStringComparitor();
}
