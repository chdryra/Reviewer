package com.chdryra.android.reviewer.Algorithms.DataAggregation.Interfaces;

import com.chdryra.android.reviewer.Algorithms.DataAggregation.Implementation.DifferenceBoolean;
import com.chdryra.android.reviewer.Algorithms.DataAggregation.Implementation.DifferenceDate;
import com.chdryra.android.reviewer.Algorithms.DataAggregation.Implementation
        .DifferenceLocation;
import com.chdryra.android.reviewer.Algorithms.DataAggregation.Implementation
        .DifferencePercentage;
import com.chdryra.android.reviewer.PlugIns.DataAggregationPlugin.DataAggregationPluginDefault.DataAggregationPluginDefault.Interfaces.ComparitorString;

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
